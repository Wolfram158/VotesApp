package ru.wolfram.vote.data.registration_for_email_code.repository

import ru.wolfram.vote.data.network.dto.toRegistrationForEmailCodeContainerDto
import ru.wolfram.vote.data.network.service.ApiService
import ru.wolfram.vote.domain.registration_for_email_code.model.RegistrationForEmailCodeContainer
import ru.wolfram.vote.domain.registration_for_email_code.repository.RegistrationForEmailCodeRepository
import javax.inject.Inject

class RegistrationForEmailCodeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RegistrationForEmailCodeRepository {
    override suspend fun registerForEmailCode(container: RegistrationForEmailCodeContainer): Result<Unit> {
        return try {
            if (apiService.registerForEmailCode(
                    container.toRegistrationForEmailCodeContainerDto()
                ).code() != 200
            ) {
                throw RuntimeException("Exception occurred when registering for email code!")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}