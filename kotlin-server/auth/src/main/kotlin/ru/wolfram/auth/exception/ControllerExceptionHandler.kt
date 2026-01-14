package ru.wolfram.auth.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.ZonedDateTime

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
                    path = request.requestURL.toString(),
                    time = ZonedDateTime.now()
                )
            )
    }
}