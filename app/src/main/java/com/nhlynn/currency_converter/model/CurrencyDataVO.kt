package com.nhlynn.currency_converter.model

import java.io.Serializable

data class CurrencyDataVO(var country: String, var unit: String, var rate: String) : Serializable
