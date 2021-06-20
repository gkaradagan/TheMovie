package com.gorkem.common.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object FavouriteConverters {

    private lateinit var moshi: Moshi

    fun initialize(moshi: Moshi) {
        this.moshi = moshi
    }

    private fun stringAdapter(): JsonAdapter<List<String>> {
        val listOfStringsType = Types.newParameterizedType(List::class.java, String::class.java)
        return moshi.adapter(listOfStringsType)
    }

    @TypeConverter
    @JvmStatic
    fun toJSON(list: List<String>?): String? = stringAdapter().toJson(list)

    @TypeConverter
    @JvmStatic
    fun fromJson(list: String): List<String>? = stringAdapter().fromJson(list)
}
