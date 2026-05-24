package com.echo.core.network.models

import com.google.gson.annotations.SerializedName
enum class City(val slug: String, val displayName: String) {
    @SerializedName("msk") MSK("msk", "Москва"),
    @SerializedName("spb") SPB("spb", "Санкт-Петербург"),
    @SerializedName("kzn") KZN("kzn", "Казань"),
    @SerializedName("ufa") UFA("ufa", "Уфа");

    companion object {
        fun fromSlug(slug: String): City? = entries.find { it.slug == slug }
    }
}