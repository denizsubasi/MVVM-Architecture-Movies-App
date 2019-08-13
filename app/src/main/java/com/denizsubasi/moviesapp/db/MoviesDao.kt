package com.denizsubasi.moviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem

@Dao
interface  MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertMovies(mostPopularsItem: MoviesResponseItem)


    @Transaction
    @Query("SELECT * FROM MoviesResponseItem WHERE responseType = :movieResponseType")
    fun fetchMovies(movieResponseType: Int): LiveData<MoviesResponseItem>



    @Transaction
    @Query("SELECT * FROM MoviesResponseItem JOIN movieObject ON movieObject.id = :movieId")
    fun fetchMovie(movieId: Int): LiveData<MovieObject>





}
