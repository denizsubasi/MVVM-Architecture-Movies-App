package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.denizsubasi.moviesapp.domain.model.MovieObject

class SpokenLanguageTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<MovieObject.SpokenLanguage> {
        val type = object : TypeToken<ArrayList<MovieObject.SpokenLanguage>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<MovieObject.SpokenLanguage>): String {
        val type = object : TypeToken<ArrayList<MovieObject.SpokenLanguage>>() {}.type
        return Gson().toJson(torrent, type)
    }
}