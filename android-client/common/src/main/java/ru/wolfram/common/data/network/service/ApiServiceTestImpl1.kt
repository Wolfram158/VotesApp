package ru.wolfram.common.data.network.service

import okhttp3.ResponseBody
import retrofit2.Response
import ru.wolfram.common.data.network.dto.RefreshWithEmailCodeContainerDto
import ru.wolfram.common.data.network.dto.RegistrationForEmailCodeContainerDto
import ru.wolfram.common.data.network.dto.RegistrationWithEmailCodeContainerDto
import ru.wolfram.common.data.network.dto.Tokens
import ru.wolfram.common.data.network.dto.VoteDto
import ru.wolfram.common.data.network.dto.VoteDto2
import ru.wolfram.common.di.AppScope

@AppScope
class ApiServiceTestImpl1(
    private val registerForEmailCodeResult: Response<String> = Response.success(
        200,
        "Success!"
    ),
    private val registerWithEmailCodeResult: Tokens = Tokens(
        token = "1234",
        refreshToken = "2345"
    ),
    private val refreshForEmailCodeResult: Response<Unit> = Response.success(
        200,
        Unit
    ),
    private val checkIfNeedEmailCodeResult: Response<Unit> = Response.error(
        400,
        ResponseBody.EMPTY
    ),
    private val refreshWithEmailCodeResult: Tokens = Tokens(
        token = "4567",
        refreshToken = "5678"
    ),
    private val createVoteResult: Response<Unit> = Response.success(
        200,
        Unit
    ),
    private val doVoteResult: List<VoteDto2> = listOf(),
    private val getVotesResult: () -> Map<String, List<VoteDto2>> = { mapOf() },
    private val getVoteResult: List<VoteDto2> = listOf()
) : ApiService {
    override suspend fun registerForEmailCode(
        registrationForEmailCodeContainerDto: RegistrationForEmailCodeContainerDto
    ): Response<String> {
        return registerForEmailCodeResult
    }

    override suspend fun registerWithEmailCode(
        registrationWithEmailCodeContainerDto: RegistrationWithEmailCodeContainerDto
    ): Tokens {
        return registerWithEmailCodeResult
    }

    override suspend fun refreshForEmailCode(username: String): Response<Unit> {
        return refreshForEmailCodeResult
    }

    override suspend fun checkIfNeedEmailCode(
        username: String,
        refreshToken: String
    ): Response<Unit> {
        return checkIfNeedEmailCodeResult
    }

    override suspend fun refreshWithEmailCode(
        refreshWithEmailCodeContainerDto: RefreshWithEmailCodeContainerDto
    ): Tokens {
        return refreshWithEmailCodeResult
    }

    override suspend fun createVote(
        votes: List<VoteDto>,
        token: String
    ): Response<Unit> {
        return createVoteResult
    }

    override suspend fun doVote(
        vote: VoteDto,
        token: String
    ): List<VoteDto2> {
        return doVoteResult
    }

    override suspend fun getVotes(token: String): Map<String, List<VoteDto2>> {
        return getVotesResult()
    }

    override suspend fun getVote(
        title: String,
        token: String
    ): List<VoteDto2> {
        return getVoteResult
    }
}