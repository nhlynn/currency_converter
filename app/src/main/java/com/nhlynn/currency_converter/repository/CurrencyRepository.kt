package com.nhlynn.currency_converter.repository

import com.nhlynn.currency_converter.network.ApiService
import com.nhlynn.currency_converter.persistence.dao.CurrencyDao
import com.nhlynn.currency_converter.persistence.table.CurrencyContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository
@Inject constructor(
    private val apiService: ApiService,
    private val currencyDao: CurrencyDao
) {
    suspend fun getExchangeRate() = apiService.getExchangeRate()

    suspend fun getCurrencies() = apiService.getCurrencies()

    suspend fun insertCurrency(content: CurrencyContent) = currencyDao.insertCurrency(content)

    val exchangeResponse: Flow<CurrencyContent?> = currencyDao.getCurrency()
}