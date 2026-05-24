package com.echo.features.event.data.models

import com.google.gson.annotations.SerializedName

data class PlaceFromKudaGoDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String,
    @SerializedName("foreignUrl") val foreignUrl: String
)