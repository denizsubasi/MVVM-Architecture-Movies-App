package com.denizsubasi.moviesapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.denizsubasi.moviesapp.data.api.ApiResponse
import com.denizsubasi.moviesapp.data.api.ApiSuccessResponse
import com.denizsubasi.moviesapp.data.api.RestApi
import com.denizsubasi.moviesapp.domain.contract.MoviesContract
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesProvider @Inject constructor(private val restApi: RestApi) : MoviesContract {


    override fun getMovieDetails(movieId: String, language: String, region: String): LiveData<ApiResponse<MovieObject>> {
        return restApi.getMovieDetails(movieId, language, region)
    }

    override fun getMostPopularMovies(page: Int, language: String, region: String): LiveData<ApiResponse<MoviesResponseItem>> {
        return Transformations.map(restApi.getMostPopularMovies(page, language, region)) { response ->
            if (response is ApiSuccessResponse) {
                response.body.responseType = MoviesResponseItem.MovieResponseTypes.MOST_POPULAR.value
            }
            response
        }
    }

    override fun getNowPlayingMovies(page: Int, language: String, region: String): LiveData<ApiResponse<MoviesResponseItem>> {
        return Transformations.map(restApi.getNowPlayingMovies(page, language, region)) { response ->
            if (response is ApiSuccessResponse) {
                response.body.responseType = MoviesResponseItem.MovieResponseTypes.NOW_PLAYING.value
            }
            response
        }
    }

    override fun getUpcomingMovies(page: Int, language: String, region: String): LiveData<ApiResponse<MoviesResponseItem>> {
        return Transformations.map(restApi.getUpcomingMovies(page, language, region)) { response ->
            if (response is ApiSuccessResponse) {
                response.body.responseType = MoviesResponseItem.MovieResponseTypes.UPCOMING.value
            }
            response
        }
    }

    override fun getMovieVideos(movieId: String): LiveData<ApiResponse<MovieObject.Videos>> {
        return restApi.getMovieVideos(movieId)
    }

}