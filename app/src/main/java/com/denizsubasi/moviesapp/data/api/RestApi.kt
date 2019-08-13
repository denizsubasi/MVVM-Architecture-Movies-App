package com.denizsubasi.moviesapp.data.api

import androidx.lifecycle.LiveData
import com.denizsubasi.moviesapp.BuildConfig
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface RestApi {

    @GET("/3/movie/popular")
    fun getMostPopularMovies(@Query("page") page: Int = 1,
                             @Query("language") language: String = Locale.getDefault().toString().replace("_", "-"),
                             @Query("region") region: String = Locale.getDefault().language, @Query("api_key") apiKey: String = BuildConfig.API_KEY): LiveData<ApiResponse<MoviesResponseItem>>


    @GET("/3/movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int = 1,
                            @Query("language") language: String = Locale.getDefault().toString().replace("_", "-"),
                            @Query("region") region: String = Locale.getDefault().language, @Query("api_key") apiKey: String = BuildConfig.API_KEY): LiveData<ApiResponse<MoviesResponseItem>>

    @GET("/3/movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int = 1,
                          @Query("language") language: String = Locale.getDefault().toString().replace("_", "-")
                          , @Query("region") region: String = Locale.getDefault().language, @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MoviesResponseItem>>


    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String, @Query("language") language: String = Locale.getDefault().toString().replace("_", "-")
                        , @Query("region") region: String = Locale.getDefault().language, @Query("append_to_response") append_to_response: String = "videos,images,credits,reviews,similar"
                        , @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MovieObject>>


    @GET("/3/movie/{movie_id}/videos")
    fun getMovieVideos(@Path("movie_id") movieId: String, @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): LiveData<ApiResponse<MovieObject.Videos>>


}