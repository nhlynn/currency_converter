package com.nhlynn.currency_converter.network

import com.nhlynn.currency_converter.network.response.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("latest")
    suspend fun getExchangeRate(
        @Query("base") base: String = "EUR"
    ): Response<CurrencyResponse>

    @GET("symbols")
    suspend fun getCurrencies(): Response<CurrencyResponse>
}