package com.nhlynn.currency_converter.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nhlynn.currency_converter.persistence.dao.CurrencyDao
import com.nhlynn.currency_converter.persistence.table.CurrencyContent
import com.nhlynn.currency_converter.persistence.type_converter.ContentTypeConverter

@Database(
    entities = [
        CurrencyContent::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ContentTypeConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun getCurrencyDao(): CurrencyDao

    companion object {
        private var dbINSTANCE: CurrencyDatabase? = null

        fun getAppDB(context: Context): CurrencyDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder(
                    context.applicationContext, CurrencyDatabase::class.java, "currency_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return dbINSTANCE!!
        }
    }
}