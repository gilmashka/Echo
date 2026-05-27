package com.echo.core.uikit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    //фон
    background = backgroundLightColor,

    //главный и вторичные цвета (кнопки, навигация?)
    primary = primaryLightColor,
    secondary = secondaryLightColor,

    //цвет содержимого на главном и вторичном цветах
    onPrimary = onPrimaryLightColor,
    onSecondary = onSecondaryLightColor,

    //цвет рамок
    outline = bordersLightColor,

    //поля ввода + текст на них
    primaryContainer = primaryContainerLightColor,
    onPrimaryContainer = onPrimaryContainerLightColor,

    secondaryContainer = secondaryContainerLightColor,
    onSecondaryContainer = onSecondaryContainerLightColor,

    //поверхности (карточки в ленте и в ЛК, ботом шиты?) + элементы на них
    surface = surfaceLightColor,
    onSurface = onSurfaceLightColor,

    //ошибка
    error = errorLightColor,
    onError = onErrorColor
)

private val DarkColorScheme = darkColorScheme(
    background = backgroundDarkColor,
    primary = primaryDarkColor,
    secondary = secondaryDarkColor,
    onPrimary = onPrimaryDarkColor,
    onSecondary = onSecondaryDarkColor,
    outline = bordersDarkColor,
    primaryContainer = primaryContainerDarkColor,
    onPrimaryContainer = onPrimaryContainerDarkColor,
    secondaryContainer = secondaryContainerDarkColor,
    onSecondaryContainer = onSecondaryContainerDarkColor,
    surface = surfaceDarkColor,
    onSurface = onSurfaceDarkColor,
    error = errorDarkColor,
    onError = onErrorDarkColor
)

@Composable
fun EchoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}