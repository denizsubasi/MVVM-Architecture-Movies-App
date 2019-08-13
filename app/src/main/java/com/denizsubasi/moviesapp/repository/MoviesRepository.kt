package com.denizsubasi.moviesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.denizsubasi.moviesapp.data.api.ApiResponse
import com.denizsubasi.moviesapp.db.MoviesDao
import com.denizsubasi.moviesapp.domain.usecase.MoviesUseCase
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import com.denizsubasi.moviesapp.vo.AppExecutors
import com.denizsubasi.moviesapp.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
public class MoviesRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        val moviesDao: MoviesDao,
        private val moviesUseCase: MoviesUseCase
) {

    fun loadMostPopularMovies(page: Int = 1): LiveData<Resource<MoviesResponseItem>> {
        return object : NetworkBoundResource<MoviesResponseItem, MoviesResponseItem>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<MoviesResponseItem>> {
                return moviesUseCase.getMostPopularMovies(page)
            }

            override fun saveCallResult(item: MoviesResponseItem) {
                moviesDao.insertMovies(item)
            }

            override fun loadFromDb(): LiveData<MoviesResponseItem> {
                return moviesDao.fetchMovies(MoviesResponseItem.MovieResponseTypes.MOST_POPULAR.value)
            }
        }.asLiveData()
    }

    fun loadNowPlayingMovies(page: Int = 1): LiveData<Resource<MoviesResponseItem>> {
        return object : NetworkBoundResource<MoviesResponseItem, MoviesResponseItem>(appExecutors) {
            override fun saveCallResult(item: MoviesResponseItem) {
                moviesDao.insertMovies(item)
            }

            override fun loadFromDb(): LiveData<MoviesResponseItem> {
                return moviesDao.fetchMovies(MoviesResponseItem.MovieResponseTypes.NOW_PLAYING.value)
            }

            override fun createCall(): LiveData<ApiResponse<MoviesResponseItem>> {

                return moviesUseCase.getNowPlayingMovies(page)

            }

        }.asLiveData()
    }


    fun loadUpcomingMovies(page: Int = 1): LiveData<Resource<MoviesResponseItem>> {
        return object : NetworkBoundResource<MoviesResponseItem, MoviesResponseItem>(appExecutors) {
            override fun saveCallResult(item: MoviesResponseItem) {
                moviesDao.insertMovies(item)
            }

            override fun loadFromDb(): LiveData<MoviesResponseItem> {
                return moviesDao.fetchMovies(MoviesResponseItem.MovieResponseTypes.UPCOMING.value)
            }

            override fun createCall(): LiveData<ApiResponse<MoviesResponseItem>> {
                return moviesUseCase.getUpComingMovies(page)

            }

        }.asLiveData()
    }

    fun getMoviesDetails(movieId: Int): LiveData<Resource<MovieObject>> {
        return object : NetworkBoundResource<MovieObject, MovieObject>(appExecutors) {
            override fun saveCallResult(item: MovieObject) {
            }

            override fun loadFromDb(): LiveData<MovieObject> {
                return moviesDao.fetchMovie(movieId)
            }

            override fun createCall(): LiveData<ApiResponse<MovieObject>> {
                return moviesUseCase.getMovieDetails(movieId)

            }

        }.asLiveData()
    }

    fun getMovieVideos(movieId: Int): LiveData<Resource<MovieObject.Videos>> {
        return object : NetworkBoundResource<MovieObject.Videos, MovieObject.Videos>(appExecutors) {
            override fun saveCallResult(item: MovieObject.Videos) {
            }

            override fun loadFromDb(): LiveData<MovieObject.Videos> {
                return Transformations.map(moviesDao.fetchMovie(movieId)) {
                    it?.videos
                }
            }

            override fun createCall(): LiveData<ApiResponse<MovieObject.Videos>> {
                return moviesUseCase.getMovieVideos(movieId)

            }

        }.asLiveData()
    }

}
