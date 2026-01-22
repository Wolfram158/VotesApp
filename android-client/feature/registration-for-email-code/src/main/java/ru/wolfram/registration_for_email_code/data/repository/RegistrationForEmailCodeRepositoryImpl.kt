package ru.wolfram.registration_for_email_code.data.repository

import android.util.Log
import ru.wolfram.common.data.network.service.ApiService
import ru.wolfram.registration_for_email_code.data.mapper.toRegistrationForEmailCodeContainerDto
import ru.wolfram.registration_for_email_code.domain.model.RegistrationForEmailCodeContainer
import ru.wolfram.registration_for_email_code.domain.repository.RegistrationForEmailCodeRepository
import javax.inject.Inject

internal class RegistrationForEmailCodeRepositoryImpl @Inject constructor(
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

    companion object {
        private val tag = RegistrationForEmailCodeRepositoryImpl::class.simpleName
    }
}