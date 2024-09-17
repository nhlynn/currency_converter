package com.nhlynn.currency_converter.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nhlynn.currency_converter.model.CurrencyDataVO
import com.nhlynn.currency_converter.network.response.CurrencyResponse
import com.nhlynn.currency_converter.persistence.table.CurrencyContent
import com.nhlynn.currency_converter.repository.CurrencyRepository
import com.nhlynn.currency_converter.utils.convertDateFormat
import com.nhlynn.currency_converter.utils.getCurrentDate
import com.nhlynn.currency_converter.utils.twoDecimalFormat
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.json.JSONObject

@HiltWorker
class CurrencyWorker
@AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val repository: CurrencyRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return coroutineScope {
            try {
                val exchangeResponse = async { repository.getExchangeRate() }.await()
                val currencyResponse = async { repository.getCurrencies() }.await()

                if (exchangeResponse.isSuccessful && currencyResponse.isSuccessful) {
                    if (exchangeResponse.body()?.isSuccess == true && currencyResponse.body()?.isSuccess == true) {
                        val saveJob =
                            async { saveData(exchangeResponse.body(), currencyResponse.body()) }
                        saveJob.await()
                        Result.success()
                    }
                    Result.failure()
                } else {
                    Result.failure()
                }
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }

    private suspend fun saveData(exchange: CurrencyResponse?, currency: CurrencyResponse?) {
        val exchangeObject = JSONObject(exchange?.rates.toString())
        val currencyObject = JSONObject(currency?.currencies.toString())

        val currencyList = ArrayList<CurrencyDataVO>()
        val updatedAt =
            if (exchange?.timestamp != null) convertDateFormat(exchange.timestamp) else getCurrentDate()

        exchangeObject.keys().forEach { key ->
            if(key!="EUR") {
                val exchangeRate = exchangeObject.getDouble(key)
                val rate = twoDecimalFormat(exchangeRate)
                val country = currencyObject.getString(key)
                currencyList.add(CurrencyDataVO(country = country, unit = key, rate = rate))
            }
        }

        withContext(Dispatchers.Main) {
            val content = CurrencyContent(1, updatedAt, currencyList)
            repository.insertCurrency(content)
        }
    }
}