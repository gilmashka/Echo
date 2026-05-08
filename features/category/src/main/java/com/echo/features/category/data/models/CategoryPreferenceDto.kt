package com.echo.features.category.data.models

import com.google.gson.annotations.SerializedName

data class CategoryPreferenceDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("preference") val preferenceType: PreferenceType?,
    @SerializedName("slug") val slug: String
) {
}

public enum class PreferenceType {
    LIKE,
    HATE,
    NEUTRAL
}
