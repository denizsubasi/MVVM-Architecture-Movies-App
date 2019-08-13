package com.denizsubasi.moviesapp.ui.flow.movies

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import com.denizsubasi.moviesapp.repository.MoviesRepository
import com.denizsubasi.moviesapp.vo.Resource
import com.denizsubasi.moviesapp.vo.Status
import javax.inject.Inject


class MoviesViewModel @Inject constructor(var repository: MoviesRepository) : ViewModel() {


    var mostPopularMoviesResultLive = MediatorLiveData<Resource<MoviesResponseItem>>()
    var nowPlayingMoviesResultLive = MediatorLiveData<Resource<MoviesResponseItem>>()
    var upcomingMoviesResultLive = MediatorLiveData<Resource<MoviesResponseItem>>()

    private fun fetchMostPopularMovies() {
        mostPopularMoviesResultLive.addSource(repository.loadMostPopularMovies()) { data ->
            mostPopularMoviesResultLive.value = data
        }
    }

    private fun fetchNowPlayingMovies() {
        nowPlayingMoviesResultLive.addSource(repository.loadNowPlayingMovies()) { data ->
            nowPlayingMoviesResultLive.value = data
        }
    }

    private fun fetchUpcomingMovies() {
        upcomingMoviesResultLive.addSource(repository.loadUpcomingMovies()) { data ->
            if (data.status != Status.LOADING) {
                fetchMostPopularMovies()
            }
            upcomingMoviesResultLive.value = data
        }
    }

    fun fetchMovies(){
        fetchUpcomingMovies()
        fetchNowPlayingMovies()
    }
}