package com.nhlynn.currency_converter.delegate

import com.nhlynn.currency_converter.model.CurrencyDataVO

interface CurrencyDelegate {
    fun onCalculateCurrencyExchange(currency: CurrencyDataVO)
}