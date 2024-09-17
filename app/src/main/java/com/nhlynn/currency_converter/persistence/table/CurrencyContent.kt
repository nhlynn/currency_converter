package com.nhlynn.currency_converter.persistence.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nhlynn.currency_converter.model.CurrencyDataVO

@Entity(tableName = "currency")
data class CurrencyContent(
    @PrimaryKey
    @ColumnInfo("id")
    var id: Int? = null,

    @ColumnInfo("updated_at")
    var updatedAt: String? = null,

    @ColumnInfo("data")
    var currencyList: ArrayList<CurrencyDataVO>? = null
)