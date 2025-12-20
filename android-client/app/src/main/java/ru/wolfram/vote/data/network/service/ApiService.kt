package ru.wolfram.vote.data.network.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.wolfram.vote.data.network.dto.RefreshWithEmailCodeContainerDto
import ru.wolfram.vote.data.network.dto.RegistrationForEmailCodeContainerDto
import ru.wolfram.vote.data.network.dto.RegistrationWithEmailCodeContainerDto
import ru.wolfram.vote.data.network.dto.Tokens
import ru.wolfram.vote.data.network.dto.VoteDto
import ru.wolfram.vote.data.network.dto.VoteDto2

interface ApiService {
    @POST("/auth/register-for-email-code")
    suspend fun registerForEmailCode(
        @Body registrationForEmailCodeContainerDto: RegistrationForEmailCodeContainerDto
    ): Response<Unit>

    @POST("/auth/register-with-email-code")
    suspend fun registerWithEmailCode(
        @Body registrationWithEmailCodeContainerDto: RegistrationWithEmailCodeContainerDto
    ): Tokens

    @POST("/auth/refresh-for-email-code?")
    suspend fun refreshForEmailCode(
        @Query("username") username: String
    ): Response<Unit>

    @GET("/auth/check-if-need-email-code?")
    suspend fun checkIfNeedEmailCode(
        @Query("username") username: String,
        @Query("refreshToken") refreshToken: String
    ): Response<Unit>

    @POST("/auth/refresh-with-email-code")
    suspend fun refreshWithEmailCode(
        @Body refreshWithEmailCodeContainerDto: RefreshWithEmailCodeContainerDto
    ): Tokens

    @POST("/votes/create-vote?")
    suspend fun createVote(
        @Body votes: List<VoteDto>,
        @Query("token") token: String
    ): Response<Unit>

    @POST("/votes/do-vote?")
    suspend fun doVote(
        @Body vote: VoteDto,
        @Query("token") token: String
    ): List<VoteDto2>

    @GET("/votes/votes?")
    suspend fun getVotes(
        @Query("token") token: String
    ): Map<String, List<VoteDto2>>

    @GET("/votes/vote?")
    suspend fun getVote(
        @Query("title") title: String,
        @Query("token") token: String
    ): List<VoteDto2>
}