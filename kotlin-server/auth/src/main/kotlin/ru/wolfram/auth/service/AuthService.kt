package ru.wolfram.auth.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.wolfram.auth.dto.RefreshTokenResult
import ru.wolfram.auth.dto.RegistrationForEmailCodeState
import ru.wolfram.auth.dto.RegistrationWithEmailCodeState
import ru.wolfram.auth.dto.UserDto
import ru.wolfram.auth.entity.RefreshToken
import ru.wolfram.auth.entity.User
import ru.wolfram.auth.entity.UserPrimaryKey
import ru.wolfram.auth.repository.RefreshTokenRepository
import ru.wolfram.auth.repository.UserRepository
import ru.wolfram.auth.security.JwtGenerator

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val mailService: MailService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtGenerator: JwtGenerator,
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value($$"${jwt.accessTokenExpiration}") private val accessTokenExpiration: Long = 900,
    @Value($$"${jwt.refreshTokenExpiration}") private val refreshTokenExpiration: Long = 900
) {
    @Transactional
    fun registerForEmailCode(user: UserDto): RegistrationForEmailCodeState {
        require(user.username.length == user.username.filter { !it.isWhitespace() }.length)
        require(user.username.isNotEmpty())
        if (userRepository.findByUserPrimaryKeyUsername(user.username) != null) {
            return RegistrationForEmailCodeState.UserAlreadyExists
        }
        if (mailService.sendEmailCode(user.username, user.email).statusCode != HttpStatus.OK) {
            return RegistrationForEmailCodeState.EmailServiceException
        }
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
            mailService.getEncodedEmailCodeByUsername(username)
        if (encoded.statusCode != HttpStatus.OK) {
            return RegistrationWithEmailCodeState.Failure
        }
        if (!passwordEncoder.matches(code, encoded.body)) {
            return RegistrationWithEmailCodeState.IncorrectCode
        }
        val (token, refreshToken) = generateTokens(username)
        userRepository.save(
            User(
                userPrimaryKey = UserPrimaryKey(username = username, email = email),
                password = passwordEncoder.encode(password)
            )
        )
        refreshTokenRepository.save(
            RefreshToken(
                refreshToken = passwordEncoder.encode(refreshToken),
                username = username
            )
        )
        return RegistrationWithEmailCodeState.Success(token, refreshToken)
    }

    @Transactional
    fun refreshToken(refreshToken: String): RefreshTokenResult {
        val username = jwtGenerator.extractUsername(refreshToken)
        val encoded =
            refreshTokenRepository.findByUsername(username) ?: return RefreshTokenResult.RefreshTokenNotFound
        if (!passwordEncoder.matches(refreshToken, encoded.refreshToken)) {
            return RefreshTokenResult.IncorrectRefreshToken
        }
        val (token, refreshToken) = generateTokens(username)
        refreshTokenRepository.deleteByUsername(username)
        refreshTokenRepository.save(
            RefreshToken(
                refreshToken = passwordEncoder.encode(refreshToken),
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
        val user = userRepository.findEmailByUserPrimaryKeyUsername(username)
        val email = user?.userPrimaryKey?.email
        require(email != null)
        require(mailService.sendEmailCode(username, email).statusCode == HttpStatus.OK)
    }

    fun generateTokens(username: String): RefreshTokenResult.Success {
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
        return RefreshTokenResult.Success(token, refreshToken)
    }

    @Transactional
    fun refreshWithEmailCode(
        username: String,
        code: String
    ): RegistrationWithEmailCodeState {
        val encoded =
            mailService.getEncodedEmailCodeByUsername(username)
        if (encoded.statusCode != HttpStatus.OK) {
            return RegistrationWithEmailCodeState.Failure
        }
        if (!passwordEncoder.matches(code, encoded.body)) {
            return RegistrationWithEmailCodeState.IncorrectCode
        }
        val (token, refreshToken) = generateTokens(username)
        refreshTokenRepository.deleteByUsername(username)
        refreshTokenRepository.save(
            RefreshToken(
                refreshToken = passwordEncoder.encode(refreshToken),
                username = username
            )
        )
        return RegistrationWithEmailCodeState.Success(token, refreshToken)
    }
}