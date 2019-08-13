package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.denizsubasi.moviesapp.domain.model.MovieObject

class CastTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<MovieObject.Credits.Cast> {
        val type = object : TypeToken<ArrayList<MovieObject.Credits.Cast>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<MovieObject.Credits.Cast>): String {
        val type = object : TypeToken<ArrayList<MovieObject.Credits.Cast>>() {}.type
        return Gson().toJson(torrent, type)
    }
}