package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.denizsubasi.moviesapp.domain.model.MovieObject

class CrewTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<MovieObject.Credits.Crew> {
        val type = object : TypeToken<ArrayList<MovieObject.Credits.Crew>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<MovieObject.Credits.Crew>): String {
        val type = object : TypeToken<ArrayList<MovieObject.Credits.Crew>>() {}.type
        return Gson().toJson(torrent, type)
    }
}