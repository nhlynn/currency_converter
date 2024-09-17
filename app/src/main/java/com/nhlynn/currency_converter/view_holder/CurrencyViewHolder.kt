package com.nhlynn.currency_converter.view_holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.nhlynn.currency_converter.databinding.CurrencyItemBinding
import com.nhlynn.currency_converter.delegate.CurrencyDelegate
import com.nhlynn.currency_converter.model.CurrencyDataVO

class CurrencyViewHolder(
    private val binding: CurrencyItemBinding,
    private val delegate: CurrencyDelegate,
) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bindData(currency: CurrencyDataVO) {
        binding.tvCountry.text = currency.country
        binding.tvCurrencyRate.text = "1 EUR = ${currency.rate}  ${currency.unit}"

        binding.cvCurrency.setOnClickListener {
            delegate.onCalculateCurrencyExchange(currency)
        }
    }

}