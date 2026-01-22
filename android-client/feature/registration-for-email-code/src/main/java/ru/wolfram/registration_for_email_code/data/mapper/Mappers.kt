package ru.wolfram.registration_for_email_code.data.mapper

import ru.wolfram.common.data.network.dto.RegistrationForEmailCodeContainerDto
import ru.wolfram.registration_for_email_code.domain.model.RegistrationForEmailCodeContainer

internal fun RegistrationForEmailCodeContainer.toRegistrationForEmailCodeContainerDto() =
    RegistrationForEmailCodeContainerDto(
        username = username,
        email = email
    )