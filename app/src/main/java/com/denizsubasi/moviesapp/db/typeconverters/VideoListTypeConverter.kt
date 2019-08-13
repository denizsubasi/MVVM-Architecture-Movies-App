package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.denizsubasi.moviesapp.domain.model.MovieObject

class VideoListTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<MovieObject.Videos.VideoObject> {
        val type = object : TypeToken<ArrayList<MovieObject.Videos.VideoObject>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<MovieObject.Videos.VideoObject>): String {
        val type = object : TypeToken<ArrayList<MovieObject.Videos.VideoObject>>() {}.type
        return Gson().toJson(torrent, type)
    }
}