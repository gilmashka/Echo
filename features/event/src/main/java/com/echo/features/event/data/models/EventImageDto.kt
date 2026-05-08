package com.echo.features.event.data.models

import com.google.gson.annotations.SerializedName

data class EventImageDto(
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("sourceName") val sourceName: String?,
    @SerializedName("sourceLink") val sourceLink: String?
)