package com.echo.core.network.models

import com.google.gson.annotations.SerializedName

data class FriendDto(
    @SerializedName("id") val id: Long,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("avatarPath") val avatarPath: String?
)