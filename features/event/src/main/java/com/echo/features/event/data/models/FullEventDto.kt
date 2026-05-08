package com.echo.features.event.data.models

import com.google.gson.annotations.SerializedName

data class FullEventDto(
    @SerializedName("event") val event: EventFromKudaGoDto,
    @SerializedName("place") val place: PlaceFromKudaGoDto?,
    @SerializedName("images") val images: List<EventImageDto>?
)