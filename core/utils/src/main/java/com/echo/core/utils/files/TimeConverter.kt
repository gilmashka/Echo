package com.echo.core.utils.files

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatEventDate(startTimestamp: Long, endTimestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("ru"))
    val startDate = Date(startTimestamp * 1000)
    val endDate = Date(endTimestamp * 1000)
    return "${formatter.format(startDate)} - ${formatter.format(endDate)}"
}