package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.denizsubasi.moviesapp.domain.model.MovieObject

class ProductionCountryTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<MovieObject.ProductionCountry> {
        val type = object : TypeToken<ArrayList<MovieObject.ProductionCountry>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<MovieObject.ProductionCountry>): String {
        val type = object : TypeToken<ArrayList<MovieObject.ProductionCountry>>() {}.type
        return Gson().toJson(torrent, type)
    }
}