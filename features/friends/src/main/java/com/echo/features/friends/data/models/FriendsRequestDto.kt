package com.echo.features.friends.data.models

import com.echo.core.network.models.FriendDto
import com.google.gson.annotations.SerializedName

data class FriendshipRequestDto(
    @SerializedName("firstFriendId") val firstFriendId: Long,
    @SerializedName("secondFriendId") val secondFriendId: Long,
    @SerializedName("friendInfo") val friendInfo: FriendDto,
    @SerializedName("status") val status: String,
    @SerializedName("isIncoming") val isIncoming: Boolean
)