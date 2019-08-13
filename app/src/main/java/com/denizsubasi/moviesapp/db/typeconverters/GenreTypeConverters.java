package com.denizsubasi.moviesapp.db.typeconverters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.denizsubasi.moviesapp.domain.model.MovieObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GenreTypeConverters {
    @TypeConverter
    public static ArrayList<MovieObject.Genre> fromString(String value) {
        Type listType = new TypeToken<ArrayList<MovieObject.Genre>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<MovieObject.Genre> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}