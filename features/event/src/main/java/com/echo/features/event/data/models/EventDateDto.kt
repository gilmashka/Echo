package com.echo.features.event.data.models

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class EventDateDto(
    @SerializedName("start") val start: Long,
    @SerializedName("end") val end: Long
) {
    val startDateTime: LocalDateTime by lazy {
        Instant.ofEpochSecond(start)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    val endDateTime: LocalDateTime by lazy {
        Instant.ofEpochSecond(end)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}