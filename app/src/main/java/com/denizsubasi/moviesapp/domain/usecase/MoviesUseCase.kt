package com.denizsubasi.moviesapp.domain.usecase

import androidx.lifecycle.LiveData
import com.denizsubasi.moviesapp.data.api.ApiResponse
import com.denizsubasi.moviesapp.domain.contract.MoviesContract
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import com.denizsubasi.moviesapp.manager.LocalizationHelpers
import java.util.*
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
        private val moviesContract: MoviesContract,
        private val localizationHelpers: LocalizationHelpers
) {

    fun getMostPopularMovies(page: Int = 1): LiveData<ApiResponse<MoviesResponseItem>> {
        return moviesContract.getMostPopularMovies(page, Locale.getDefault().toString().replace("_", "-"), localizationHelpers.detectCountry())
    }

    fun getNowPlayingMovies(page: Int = 1): LiveData<ApiResponse<MoviesResponseItem>> {
        return moviesContract.getNowPlayingMovies(page, Locale.getDefault().toString().replace("_", "-"), localizationHelpers.detectCountry())
    }

    fun getUpComingMovies(page: Int = 1): LiveData<ApiResponse<MoviesResponseItem>> {
        return moviesContract.getUpcomingMovies(page, Locale.getDefault().toString().replace("_", "-"), localizationHelpers.detectCountry())
    }

    fun getMovieDetails(movieId: Int): LiveData<ApiResponse<MovieObject>> {
        return moviesContract.getMovieDetails(movieId.toString(), Locale.getDefault().toString().replace("_", "-"), localizationHelpers.detectCountry())
    }

    fun getMovieVideos(movieId: Int): LiveData<ApiResponse<MovieObject.Videos>> {
        return moviesContract.getMovieVideos(movieId.toString())
    }


}