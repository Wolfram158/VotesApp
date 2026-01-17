package ru.wolfram.auth.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.wolfram.auth.dto.*
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
        if (encoded.statusCode == HttpStatus.NOT_FOUND) {
            return RegistrationWithEmailCodeState.UsernameNotFound
        }
        if (encoded.statusCode != HttpStatus.OK) {
            return RegistrationWithEmailCodeState.EmailServiceException
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
    fun refreshForEmailCode(userDto: UserDto2): RefreshForEmailCodeState {
        val user = userRepository.findEmailByUserPrimaryKeyUsername(userDto.username)
            ?: return RefreshForEmailCodeState.UserNotFound
        val email = user.userPrimaryKey?.email ?: return RefreshForEmailCodeState.ImpossibleState
        if (!passwordEncoder.matches(userDto.password, user.password)) {
            return RefreshForEmailCodeState.WrongPassword
        }
        if (mailService.sendEmailCode(userDto.username, email).statusCode != HttpStatus.OK) {
            return RefreshForEmailCodeState.EmailServiceException
        }
        return RefreshForEmailCodeState.Success
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
    ): RefreshWithEmailCodeState {
        val encoded =
            mailService.getEncodedEmailCodeByUsername(username)
        if (encoded.statusCode == HttpStatus.NOT_FOUND) {
            return RefreshWithEmailCodeState.UsernameNotFound
        }
        if (encoded.statusCode != HttpStatus.OK) {
            return RefreshWithEmailCodeState.EmailServiceException
        }
        if (!passwordEncoder.matches(code, encoded.body)) {
            return RefreshWithEmailCodeState.IncorrectCode
        }
        val (token, refreshToken) = generateTokens(username)
        refreshTokenRepository.deleteByUsername(username)
        refreshTokenRepository.save(
            RefreshToken(
                refreshToken = passwordEncoder.encode(refreshToken),
                username = username
            )
        )
        return RefreshWithEmailCodeState.Success(token, refreshToken)
    }
}