package ru.wolfram.registration_with_email_code.data.mapper

import ru.wolfram.common.data.network.dto.RegistrationWithEmailCodeContainerDto
import ru.wolfram.registration_with_email_code.domain.model.RegistrationWithEmailCodeContainer

internal fun RegistrationWithEmailCodeContainer.toRegistrationWithEmailCodeContainerDto() =
    RegistrationWithEmailCodeContainerDto(
        username = username,
        code = code,
        email = email,
        password = password
    )