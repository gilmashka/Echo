package com.echo.features.event.data.models

import com.google.gson.annotations.SerializedName

data class EventFromKudaGoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("bodyText") val bodyText: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("siteUrl") val siteUrl: String?,
    @SerializedName("dates") val dates: List<EventDateDto>?,
    @SerializedName("placeId") val placeId: Int?,
    @SerializedName("imageUrl") val imageUrl: String?
)