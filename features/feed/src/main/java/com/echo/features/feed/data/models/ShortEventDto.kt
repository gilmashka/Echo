package com.echo.features.feed.data.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class ShortEventDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: String,
    @SerializedName("dates") val dates: List<EventDateDto>,
    @SerializedName("imageUrl") val imageUrl: String
)
@RequiresApi(Build.VERSION_CODES.O)
data class EventDateDto(
    @SerializedName("start") val start: Long,
    @SerializedName("end") val end: Long
){
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