package com.denizsubasi.moviesapp.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.denizsubasi.moviesapp.db.typeconverters.*
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem

/**
 * Main database description.
 */
@Database(
        entities = [
            MoviesResponseItem::class, MovieObject::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(*[MostPopularDataConverter::class, StringTypeConverter::class, com.denizsubasi.moviesapp.db.typeconverters.GenreTypeConverters::class, com.denizsubasi.moviesapp.db.typeconverters.ProductionCompanyTypeConverters::class,
    ProductionCountryTypeConverter::class, SpokenLanguageTypeConverter::class,
    CastTypeConverter::class,
    CrewTypeConverter::class,
    SimilarVideosTypeConverter::class,
    VideoListTypeConverter::class, ReviewTypeConverter::class])
abstract class MoviesAppDB : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
