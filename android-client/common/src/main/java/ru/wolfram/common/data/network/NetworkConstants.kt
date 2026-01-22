package ru.wolfram.common.data.network

object NetworkConstants {
    const val AUTHORIZATION_HEADER = "Authorization"
    const val REGISTER_FOR_EMAIL_CODE_ENDPOINT = "auth/register-for-email-code"
    const val REGISTER_WITH_EMAIL_CODE_ENDPOINT = "auth/register-with-email-code"
    const val REFRESH_FOR_EMAIL_CODE_ENDPOINT = "auth/refresh-for-email-code"
    const val CHECK_IF_NEED_EMAIL_CODE_ENDPOINT = "auth/check-if-need-email-code"
    const val REFRESH_WITH_EMAIL_CODE_ENDPOINT = "auth/refresh-with-email-code"
    const val REFRESH_TOKEN_ENDPOINT = "auth/refresh-token"
    const val CREATE_VOTE_ENDPOINT = "write-votes/create-vote"
    const val DO_VOTE_ENDPOINT = "read-votes/do-vote"
    const val VOTES_ENDPOINT = "read-votes/votes"
    const val VOTE_ENDPOINT = "read-votes/vote"
    const val USERNAME_QUERY_PARAM = "username"
}