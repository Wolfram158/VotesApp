package ru.wolfram.auth.exception

import java.time.ZonedDateTime

data class ErrorResponse(
    val msg: String,
    val path: String,
    val time: ZonedDateTime
) {
    companion object {
        const val MSG_1 =
            "Check that username is not empty and it does not contain whitespace. Also check correctness of email."
    }
}
