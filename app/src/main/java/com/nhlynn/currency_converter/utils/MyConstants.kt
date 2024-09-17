package com.nhlynn.currency_converter.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val API_KEY = "hkxsbEU5DwEdsTuqYmpNEoeyU4GIy4SP"

const val CURRENCY = "currency"

fun twoDecimalFormat(amount: Any?): String {
    return String.format(Locale.ENGLISH, "%.2f", amount)
}

val dateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a", Locale.ENGLISH)
fun convertDateFormat(timeStamp: Long): String {
    val uTimeStamp = timeStamp * 1000
    return dateFormat.format(Date(uTimeStamp))
}

fun getCurrentDate(): String {
    return dateFormat.format(Date())
}