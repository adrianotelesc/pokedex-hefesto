package com.hefesto.pokedex.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromStringList(strings: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(strings, type)
    }

    @TypeConverter
    fun toStringList(string: String): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, type)
    }
}