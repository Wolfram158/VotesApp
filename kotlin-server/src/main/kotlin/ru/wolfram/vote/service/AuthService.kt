package ru.wolfram.vote.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.wolfram.vote.dto.*
import ru.wolfram.vote.entity.RefreshToken
import ru.wolfram.vote.entity.Session
import ru.wolfram.vote.entity.User
import ru.wolfram.vote.entity.UserPrimaryKey
import ru.wolfram.vote.repository.RefreshTokenRepository
import ru.wolfram.vote.repository.SessionRepository
import ru.wolfram.vote.repository.UserRepository
import ru.wolfram.vote.security.JwtGenerator
import ru.wolfram.vote.security.SecurityUtils
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val mailSender: MailSender,
    private val securityUtils: SecurityUtils,
    private val passwordEncoder: PasswordEncoder,
    private val sessionRepository: SessionRepository,
    private val jwtGenerator: JwtGenerator,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    @Value($$"${mail.codeLength}")
    private val codeLength: Int = 30

    @Value($$"${mail.codeExpirationSeconds}")
    private val codeExpirationSeconds: Long = 600

    @Value($$"${jwt.accessTokenExpiration}")
    private val accessTokenExpiration: Long = 900

    @Value($$"${jwt.refreshTokenExpiration}")
    private val refreshTokenExpiration: Long = 900

    @Transactional
    fun checkIfNeedEmailCode(username: String, refreshToken: String): CheckIfNeedEmailCodeState {
        if (userRepository.findByUserPrimaryKeyUsername(username) == null) {
            return CheckIfNeedEmailCodeState.UserNotFound
        }
        val encoded =
            refreshTokenRepository.findByUsername(username) ?: return CheckIfNeedEmailCodeState.No
        val validationResult = validateToken(refreshToken)
        if (validationResult !is TokenValidationResult.Success) {
            return CheckIfNeedEmailCodeState.No
        }
        if (passwordEncoder.matches(refreshToken.substring(0..35), encoded.refreshToken)) {
            return CheckIfNeedEmailCodeState.No
        }
        return CheckIfNeedEmailCodeState.Yes
    }

    @Transactional
    fun registerForEmailCode(user: UserDto): RegistrationForEmailCodeState {
        if (userRepository.findByUserPrimaryKeyUsername(user.username) != null) {
            return RegistrationForEmailCodeState.UserAlreadyExists
        }
        val code = securityUtils.randomString(codeLength)
        val encoded = passwordEncoder.encode(code)
        sessionRepository.save(
            Session(
                username = user.username,
                code = encoded!!,
                expirationInSeconds = codeExpirationSeconds
            )
        )
        mailSender.send(user.email, "Access code", "Your code: $code")
        return RegistrationForEmailCodeState.Success
    }

    @Transactional
    fun registerWithEmailCode(
        username: String,
        email: String,
        password: String,
        code: String
    ): RegistrationWithEmailCodeState {
        val encoded =
            sessionRepository.findById(username).getOrNull() ?: return RegistrationWithEmailCodeState.CodeNotFound
        if (!passwordEncoder.matches(code, encoded.code)) {
            return RegistrationWithEmailCodeState.IncorrectCode
        }
        sessionRepository.deleteById(username)
        val time = System.currentTimeMillis()
        val token = jwtGenerator.generateToken(
            subject = username,
            expirationDelta = accessTokenExpiration,
            notBeforeDelta = 0,
            nowMillis = time,
            additionalClaims = mapOf()
        )
        val refreshToken = jwtGenerator.generateToken(
            subject = username,
            expirationDelta = refreshTokenExpiration,
            notBeforeDelta = accessTokenExpiration,
            nowMillis = time,
            additionalClaims = mapOf()
        )
        userRepository.save(
            User(
                userPrimaryKey = UserPrimaryKey(username = username, email = email),
                password = passwordEncoder.encode(password)
            )
        )
        refreshTokenRepository.save(
            RefreshToken(
                refreshToken = passwordEncoder.encode(refreshToken.substring(0..35)),
                username = username
            )
        )
        return RegistrationWithEmailCodeState.Success(token, refreshToken)
    }

    fun validateToken(token: String): TokenValidationResult {
        try {
            val claims = jwtGenerator.extractAllClaims(token)
            val exp = claims.expiration
            val nbf = claims.notBefore
            val time = Date(System.currentTimeMillis())
            if (time !in nbf..exp) {
                println("Bad time")
                return TokenValidationResult.BadTime
            }
            return TokenValidationResult.Success(claims)
        } catch (e: Exception) {
            println(e.message)
            return TokenValidationResult.BadToken
        }
    }

    @Transactional
    fun refreshToken(refreshToken: String): RefreshTokenResult {
        val validationResult = validateToken(refreshToken)
        if (validationResult !is TokenValidationResult.Success) {
            return RefreshTokenResult.IncorrectRefreshToken
        }
        val username = validationResult.claims.subject
        val encoded =
            refreshTokenRepository.findByUsername(username) ?: return RefreshTokenResult.RefreshTokenNotFound
        if (!passwordEncoder.matches(refreshToken.substring(0..35), encoded.refreshToken)) {
            return RefreshTokenResult.IncorrectRefreshToken
        }
        val time = System.currentTimeMillis()
        val token = jwtGenerator.generateToken(
            subject = username,
            expirationDelta = accessTokenExpiration,
            notBeforeDelta = 0,
            nowMillis = time,
            additionalClaims = mapOf()
        )
        val refreshToken = jwtGenerator.generateToken(
            subject = username,
            expirationDelta = refreshTokenExpiration,
            notBeforeDelta = accessTokenExpiration,
            nowMillis = time,
            additionalClaims = mapOf()
        )
        refreshTokenRepository.deleteByUsername(username)
        refreshTokenRepository.save(
            RefreshToken(
                refreshToken = passwordEncoder.encode(refreshToken.substring(0..35)),
                username = username
            )
        )
        return RefreshTokenResult.Success(
            token = token,
            refreshToken = refreshToken
        )
    }

    @Transactional
    fun refreshForEmailCode(username: String) {
        val code = securityUtils.randomString(codeLength)
        val encoded = passwordEncoder.encode(code)
        sessionRepository.save(
            Session(
                username = username,
                code = encoded!!,
                expirationInSeconds = codeExpirationSeconds
            )
        )
        val email = userRepository.findEmailByUserPrimaryKeyUsername(username)?.userPrimaryKey?.email
        require(email != null)
        mailSender.send(email, "Access code", "Your code: $code")
    }

    @Transactional
    fun refreshWithEmailCode(
        username: String,
        code: String
    ): RegistrationWithEmailCodeState {
        val encoded =
            sessionRepository.findById(username).getOrNull() ?: return RegistrationWithEmailCodeState.CodeNotFound
        if (!passwordEncoder.matches(code, encoded.code)) {
            return RegistrationWithEmailCodeState.IncorrectCode
        }
        sessionRepository.deleteById(username)
        val time = System.currentTimeMillis()
        val token = jwtGenerator.generateToken(
            subject = username,
            expirationDelta = accessTokenExpiration,
            notBeforeDelta = 0,
            nowMillis = time,
            additionalClaims = mapOf()
        )
        val refreshToken = jwtGenerator.generateToken(
            subject = username,
            expirationDelta = refreshTokenExpiration,
            notBeforeDelta = accessTokenExpiration,
            nowMillis = time,
            additionalClaims = mapOf()
        )
        refreshTokenRepository.deleteByUsername(username)
        refreshTokenRepository.save(
            RefreshToken(
                refreshToken = passwordEncoder.encode(refreshToken.substring(0..35)),
                username = username
            )
        )
        return RegistrationWithEmailCodeState.Success(token, refreshToken)
    }
}