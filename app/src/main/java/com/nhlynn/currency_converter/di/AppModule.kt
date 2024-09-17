package com.nhlynn.currency_converter.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhlynn.currency_converter.network.ApiService
import com.nhlynn.currency_converter.persistence.CurrencyDatabase
import com.nhlynn.currency_converter.persistence.dao.CurrencyDao
import com.nhlynn.currency_converter.utils.API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun gSon(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun client(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val origin: Request = chain.request()
                val requestBuilder: Request.Builder = origin.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept", "text/plain")
                    .addHeader("apikey", API_KEY)

                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(gSon: Gson, client: OkHttpClient): ApiService =
        Retrofit
            .Builder()
            .baseUrl("https://api.apilayer.com/exchangerates_data/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .build()
            .create(ApiService::class.java)

    @Singleton
    @Provides
    fun getAppDB(context: Application): CurrencyDatabase {
        return CurrencyDatabase.getAppDB(context)
    }

    @Singleton
    @Provides
    fun getAppDao(appDB: CurrencyDatabase): CurrencyDao {
        return appDB.getCurrencyDao()
    }

}