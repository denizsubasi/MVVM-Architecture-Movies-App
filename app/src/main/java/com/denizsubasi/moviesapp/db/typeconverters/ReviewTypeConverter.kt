package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.denizsubasi.moviesapp.domain.model.MovieObject

class ReviewTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<MovieObject.Reviews.Review> {
        val type = object : TypeToken<ArrayList<MovieObject.Reviews.Review>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<MovieObject.Reviews.Review>): String {
        val type = object : TypeToken<ArrayList<MovieObject.Reviews.Review>>() {}.type
        return Gson().toJson(torrent, type)
    }
}