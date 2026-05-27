package com.echo.features.friends.data.models

import com.google.gson.annotations.SerializedName

data class FriendshipRequestForm(
    @SerializedName("recipientId") val recipientId: Long,
    @SerializedName("userFriendshipInvitationStatus") val status: String
)