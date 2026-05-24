package com.echo.features.profile.data.models

import com.echo.core.network.models.City
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Long,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("city") val city: City,
    @SerializedName("avatarPath") val avatarPath: String?
)