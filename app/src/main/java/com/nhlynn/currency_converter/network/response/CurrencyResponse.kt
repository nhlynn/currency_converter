package com.nhlynn.currency_converter.network.response

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("success") val isSuccess: Boolean,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("rates") val rates: JsonObject,
    @SerializedName("symbols") val currencies: JsonObject
)
