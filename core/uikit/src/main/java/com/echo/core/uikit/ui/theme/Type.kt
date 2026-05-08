package com.echo.core.uikit.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.echo.core.uikit.R


val SansationFontFamily = FontFamily(
    Font(resId = R.font.sansation_light, weight = FontWeight.Light),
    Font(resId = R.font.sansation_regular, weight = FontWeight.Normal),
    Font(resId = R.font.sansation_bold, weight = FontWeight.Bold),
    Font(resId = R.font.sansation_lightitalic, weight = FontWeight.ExtraLight) //==Italic
)
val Typography = Typography(
    labelSmall = TextStyle( //placeholder в полях поиска
        fontFamily = SansationFontFamily,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    labelMedium = TextStyle( //текс в полях поиска
        fontFamily = SansationFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),

    titleMedium = TextStyle( //кнопки
        fontFamily = SansationFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    displayLarge = TextStyle(
        fontFamily = SansationFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = SansationFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displaySmall = TextStyle(
        fontFamily = SansationFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

