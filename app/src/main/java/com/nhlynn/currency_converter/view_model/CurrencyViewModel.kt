package com.nhlynn.currency_converter.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nhlynn.currency_converter.persistence.table.CurrencyContent
import com.nhlynn.currency_converter.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel
@Inject constructor(repository: CurrencyRepository) : ViewModel() {
    val currencyResponse: LiveData<CurrencyContent?> = repository.exchangeResponse.asLiveData()
}