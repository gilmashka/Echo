package com.echo.features.feed.domain.models

import com.echo.features.feed.data.models.ShortEventDto

data class FullFeed (
    val favouriteFeed: List<ShortEventDto>,
    val neutralFeed: List<ShortEventDto>,
    val showPlaceholder: Boolean
)
