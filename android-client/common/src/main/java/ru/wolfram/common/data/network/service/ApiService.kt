package ru.wolfram.common.data.network.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.wolfram.common.data.network.NetworkConstants
import ru.wolfram.common.data.network.dto.RefreshWithEmailCodeContainerDto
import ru.wolfram.common.data.network.dto.RegistrationForEmailCodeContainerDto
import ru.wolfram.common.data.network.dto.RegistrationWithEmailCodeContainerDto
import ru.wolfram.common.data.network.dto.TitleDto
import ru.wolfram.common.data.network.dto.TitlesDto
import ru.wolfram.common.data.network.dto.Tokens
import ru.wolfram.common.data.network.dto.UserDto2
import ru.wolfram.common.data.network.dto.VoteDto
import ru.wolfram.common.data.network.dto.VoteDto2

interface ApiService {
    @POST(NetworkConstants.REGISTER_FOR_EMAIL_CODE_ENDPOINT)
    suspend fun registerForEmailCode(
        @Body registrationForEmailCodeContainerDto: RegistrationForEmailCodeContainerDto
    ): Response<String>

    @POST(NetworkConstants.REGISTER_WITH_EMAIL_CODE_ENDPOINT)
    suspend fun registerWithEmailCode(
        @Body registrationWithEmailCodeContainerDto: RegistrationWithEmailCodeContainerDto
    ): Tokens

    @POST(NetworkConstants.REFRESH_FOR_EMAIL_CODE_ENDPOINT)
    suspend fun refreshForEmailCode(
        @Body userDto2: UserDto2
    ): Response<Unit>

    @GET(NetworkConstants.CHECK_IF_NEED_EMAIL_CODE_ENDPOINT)
    suspend fun checkIfNeedEmailCode(
        @Query(NetworkConstants.USERNAME_QUERY_PARAM) username: String,
        @Header(NetworkConstants.AUTHORIZATION_HEADER) refreshToken: String
    ): Response<Unit>

    @POST(NetworkConstants.REFRESH_WITH_EMAIL_CODE_ENDPOINT)
    suspend fun refreshWithEmailCode(
        @Body refreshWithEmailCodeContainerDto: RefreshWithEmailCodeContainerDto
    ): Tokens

    @POST(NetworkConstants.CREATE_VOTE_ENDPOINT)
    suspend fun createVote(
        @Body votes: List<VoteDto>,
        @Header(NetworkConstants.AUTHORIZATION_HEADER) token: String
    ): Response<Unit>

    @POST(NetworkConstants.DO_VOTE_ENDPOINT)
    suspend fun doVote(
        @Body vote: VoteDto,
        @Query(NetworkConstants.USERNAME_QUERY_PARAM) username: String,
        @Header(NetworkConstants.AUTHORIZATION_HEADER) token: String
    ): List<VoteDto2>

    @GET(NetworkConstants.VOTES_ENDPOINT)
    suspend fun getVotes(
        @Query("page") page: Int,
        @Header(NetworkConstants.AUTHORIZATION_HEADER) token: String
    ): TitlesDto

    @GET(NetworkConstants.VOTE_ENDPOINT)
    suspend fun getVote(
        @Body titleDto: TitleDto,
        @Header(NetworkConstants.AUTHORIZATION_HEADER) token: String
    ): List<VoteDto2>
}