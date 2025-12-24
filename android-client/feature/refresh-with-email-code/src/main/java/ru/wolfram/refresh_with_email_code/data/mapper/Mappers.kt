package ru.wolfram.refresh_with_email_code.data.mapper

import ru.wolfram.common.data.network.dto.RefreshWithEmailCodeContainerDto
import ru.wolfram.refresh_with_email_code.domain.model.RefreshWithEmailCodeContainer

internal fun RefreshWithEmailCodeContainer.toRefreshWithEmailCodeContainerDto() =
    RefreshWithEmailCodeContainerDto(
        username = username,
        code = code
    )