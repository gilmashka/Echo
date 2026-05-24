package com.echo.core.network.models

import com.google.gson.annotations.SerializedName

data class UserForm(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("password") val password: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("city") val city: City
)