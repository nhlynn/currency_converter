package com.nhlynn.currency_converter.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nhlynn.currency_converter.databinding.ActivityMainBinding
import com.nhlynn.currency_converter.adapter.CurrencyAdapter
import com.nhlynn.currency_converter.delegate.CurrencyDelegate
import com.nhlynn.currency_converter.model.CurrencyDataVO
import com.nhlynn.currency_converter.utils.CURRENCY
import com.nhlynn.currency_converter.view_model.CurrencyViewModel
import com.nhlynn.currency_converter.worker.CurrencyWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CurrencyDelegate {
    private lateinit var binding: ActivityMainBinding

    private val mCurrencyViewModel: CurrencyViewModel by viewModels()

    private lateinit var mAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()
        scheduleWork()
        waitResponse()

    }

    @SuppressLint("SetTextI18n")
    private fun waitResponse() {
        mCurrencyViewModel.currencyResponse.observe(this) { response ->
            if (!response?.currencyList.isNullOrEmpty()) {
                binding.progress.visibility = View.GONE
                binding.tvUpdatedAt.text = "Updated At : ${response?.updatedAt}"
                mAdapter.setData(response?.currencyList!!)
            }
        }
    }

    private fun setUpAdapter() {
        mAdapter = CurrencyAdapter(this)
        binding.rvCurrency.setHasFixedSize(true)
        binding.rvCurrency.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rvCurrency.adapter = mAdapter
    }

    private fun scheduleWork() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        val workRequest =
            PeriodicWorkRequestBuilder<CurrencyWorker>(30, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            CURRENCY, ExistingPeriodicWorkPolicy.UPDATE, workRequest
        )
    }

    override fun onCalculateCurrencyExchange(currency: CurrencyDataVO) {
        startActivity(
            CalculateCurrencyActivity.newIntent(this)
                .putExtra(CURRENCY, currency)
        )
    }
}