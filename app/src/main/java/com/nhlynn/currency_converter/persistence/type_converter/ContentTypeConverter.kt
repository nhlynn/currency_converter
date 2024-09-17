package com.nhlynn.currency_converter.persistence.type_converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhlynn.currency_converter.model.CurrencyDataVO

class ContentTypeConverter {
    @TypeConverter
    fun contentToString(content: ArrayList<CurrencyDataVO>): String {
        return Gson().toJson(content)
    }

    @TypeConverter
    fun stringToContent(contentJson: String): ArrayList<CurrencyDataVO> {
        val contentType = object : TypeToken<ArrayList<CurrencyDataVO>>() {}.type
        return Gson().fromJson(contentJson, contentType)
    }

}