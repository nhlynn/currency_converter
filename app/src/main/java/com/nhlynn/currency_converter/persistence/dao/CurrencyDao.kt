package com.nhlynn.currency_converter.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nhlynn.currency_converter.persistence.table.CurrencyContent
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(content: CurrencyContent)

    @Query("SELECT * FROM currency")
    fun getCurrency(): Flow<CurrencyContent?>
}