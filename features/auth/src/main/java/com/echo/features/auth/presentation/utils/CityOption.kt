package com.echo.features.auth.presentation.utils

data class CityOption(
    val slug: String,
    val name: String
)

val cityOptions = listOf(
    CityOption("msk", "Москва"),
    CityOption("spb", "Санкт-Петербург"),
    CityOption("kzn", "Казань"),
    CityOption("ufa", "Уфа")
)