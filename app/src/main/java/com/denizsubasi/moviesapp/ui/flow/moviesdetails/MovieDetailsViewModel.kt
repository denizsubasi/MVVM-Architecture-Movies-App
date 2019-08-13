package com.denizsubasi.moviesapp.ui.flow.moviesdetails


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.denizsubasi.moviesapp.domain.model.MovieObject
import com.denizsubasi.moviesapp.repository.MoviesRepository
import com.denizsubasi.moviesapp.vo.Resource
import com.denizsubasi.moviesapp.vo.Status
import javax.inject.Inject



public class MovieDetailsViewModel @Inject constructor(var repository: MoviesRepository) : ViewModel() {


    val movieDetailsLive = MediatorLiveData<Resource<MovieObject>>()

    fun fetchMovieDetails(movieId: Int) {
        movieDetailsLive.addSource(repository.getMoviesDetails(movieId)) {
            val movieDetailsResponse = it
            movieDetailsLive.addSource(repository.getMovieVideos(movieId)) { videoResponse ->
                if (movieDetailsResponse.status == Status.SUCCESS && videoResponse.status == Status.SUCCESS) {
                    videoResponse.data?.videoList?.let { it1 -> movieDetailsResponse.data?.videos?.videoList?.addAll(it1) }
                }
                movieDetailsLive.value = movieDetailsResponse
            }
        }

    }


}