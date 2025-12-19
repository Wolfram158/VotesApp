package ru.wolfram.vote.data.gateway.repository

import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.domain.gateway.model.RefreshForEmailCodeContainer
import ru.wolfram.vote.domain.gateway.repository.RefreshForEmailCodeRepository
import javax.inject.Inject

class RefreshForEmailCodeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RefreshForEmailCodeRepository {
    override suspend fun refreshForEmailCode(container: RefreshForEmailCodeContainer) {
        val response = apiService.refreshForEmailCode(
            username = container.username
        )
        if (response.code() != 200) {
            throw RuntimeException("Exception occurred when getting email code for refreshing!")
        }
    }

}