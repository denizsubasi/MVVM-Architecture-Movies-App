package com.denizsubasi.moviesapp.domain.contract

import androidx.lifecycle.LiveData
import com.denizsubasi.moviesapp.data.api.ApiResponse
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem

interface MoviesContract {
    fun getMostPopularMovies(page: Int = 1, language: String,region:String): LiveData<ApiResponse<MoviesResponseItem>>
    fun getNowPlayingMovies(page: Int = 1,language: String,region: String): LiveData<ApiResponse<MoviesResponseItem>>
    fun getUpcomingMovies(page: Int = 1,language: String,region: String): LiveData<ApiResponse<MoviesResponseItem>>
    fun getMovieDetails(movieId:String,language: String,region: String): LiveData<ApiResponse<MovieObject>>
    fun getMovieVideos(movieId: String): LiveData<ApiResponse<MovieObject.Videos>>

}