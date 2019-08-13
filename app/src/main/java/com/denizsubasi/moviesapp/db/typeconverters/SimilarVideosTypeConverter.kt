package com.denizsubasi.moviesapp.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.denizsubasi.moviesapp.domain.model.MovieObject

class SimilarVideosTypeConverter {
    @TypeConverter
    fun toResult(json: String): ArrayList<MovieObject.SimilarVideos.SimilarVideoObject> {
        val type = object : TypeToken<ArrayList<MovieObject.SimilarVideos.SimilarVideoObject>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: ArrayList<MovieObject.SimilarVideos.SimilarVideoObject>): String {
        val type = object : TypeToken<ArrayList<MovieObject.SimilarVideos.SimilarVideoObject>>() {}.type
        return Gson().toJson(torrent, type)
    }
}