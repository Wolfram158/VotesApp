package ru.wolfram.auth.exception

import io.jsonwebtoken.PrematureJwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestOnIllegalArgumentException(request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    msg = ErrorResponse.MSG_1,
                    path = request.requestURL.toString()
                )
            )
    }

    @ExceptionHandler(PrematureJwtException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestOnPrematureJwtException(request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    msg = ErrorResponse.MSG_2,
                    path = request.requestURL.toString()
                )
            )
    }

}