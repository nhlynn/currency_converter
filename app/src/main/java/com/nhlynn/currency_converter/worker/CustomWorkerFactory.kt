package com.nhlynn.currency_converter.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.nhlynn.currency_converter.repository.CurrencyRepository
import com.nhlynn.currency_converter.worker.CurrencyWorker
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(private val repository: CurrencyRepository) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context, workerClassName: String, workerParameters: WorkerParameters
    ): ListenableWorker = CurrencyWorker(appContext, workerParameters, repository)
}