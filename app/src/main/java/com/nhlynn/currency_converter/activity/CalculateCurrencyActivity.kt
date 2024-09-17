package com.nhlynn.currency_converter.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.nhlynn.currency_converter.databinding.ActivityCalculateCurrencyBinding
import com.nhlynn.currency_converter.model.CurrencyDataVO
import com.nhlynn.currency_converter.utils.CURRENCY
import com.nhlynn.currency_converter.utils.twoDecimalFormat

class CalculateCurrencyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currency = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(CURRENCY, CurrencyDataVO::class.java)
        } else {
            intent.getSerializableExtra(CURRENCY) as CurrencyDataVO
        }

        bindData(currency)
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(currency: CurrencyDataVO?) {
        binding.tvDescription.text = "1 EUR = ${currency?.rate} ${currency?.unit}"
        binding.lblCurrency.text = "${currency?.country} (${currency?.unit})"
        binding.edtAmount.setText("1")
        binding.tvCurrency.text = currency?.rate

        binding.edtAmount.addTextChangedListener {
            calculateExchangeRate(currency?.rate)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateExchangeRate(exchangeRate: String?) {
        if (binding.edtAmount.text.isNullOrEmpty()) {
            binding.tvCurrency.text = "0"
        } else {
            if (binding.edtAmount.text.toString() == ".") {
                binding.edtAmount.setText("0.")
                binding.edtAmount.setSelection(binding.edtAmount.length())
            }
            val rate = exchangeRate?.toDouble() ?: 0.0

            val amount = (binding.edtAmount.text.toString().toDouble()) * rate
            binding.tvCurrency.text = twoDecimalFormat(amount)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CalculateCurrencyActivity::class.java)
        }
    }
}