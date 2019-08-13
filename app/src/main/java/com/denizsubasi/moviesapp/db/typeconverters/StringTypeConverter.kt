package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<String> {
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<String>): String {
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().toJson(torrent, type)
    }
}