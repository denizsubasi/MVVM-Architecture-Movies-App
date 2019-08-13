package com.denizsubasi.moviesapp.ui.flow.specificcategorymovielistflow


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.denizsubasi.moviesapp.domain.model.MoviesResponseItem
import com.denizsubasi.moviesapp.repository.MoviesRepository
import com.denizsubasi.moviesapp.vo.Resource
import javax.inject.Inject


public class SpecificCategoryMoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {


    var moviesLive = MediatorLiveData<Resource<MoviesResponseItem>>()

    fun loadMovies(responseType: Int, currentPage: Int) {
        when (responseType) {
            MoviesResponseItem.MovieResponseTypes.MOST_POPULAR.value -> {
                moviesLive.addSource(repository.loadMostPopularMovies(currentPage)) { data ->
                    moviesLive.value = data
                }
            }
            MoviesResponseItem.MovieResponseTypes.NOW_PLAYING.value -> {
                moviesLive.addSource(repository.loadNowPlayingMovies(currentPage)) { data ->
                    moviesLive.value = data
                }
            }
            MoviesResponseItem.MovieResponseTypes.UPCOMING.value -> {
                moviesLive.addSource(repository.loadUpcomingMovies(currentPage)) { data ->
                    moviesLive.value = data
                }
            }
        }
    }
}