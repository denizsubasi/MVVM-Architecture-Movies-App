package com.denizsubasi.moviesapp.data.mapper

import com.denizsubasi.moviesapp.domain.model.MovieObject
import javax.inject.Inject

class MoviesDataMapper @Inject constructor()

fun convertSimilarVideoToMovieObject(similarVideo: MovieObject.SimilarVideos.SimilarVideoObject): MovieObject {
    val movieObject = MovieObject()
    movieObject.adult = similarVideo.adult
    movieObject.backdropPath = similarVideo.backdropPath
    movieObject.id = similarVideo.similarVideoId
    movieObject.originalLanguage = similarVideo.originalLanguage
    movieObject.originalTitle = similarVideo.originalTitle
    movieObject.overview = similarVideo.overview
    movieObject.popularity = similarVideo.popularity
    movieObject.posterPath = similarVideo.posterPath
    movieObject.releaseDate = similarVideo.releaseDate
    movieObject.title = similarVideo.title ?: ""
    movieObject.video = similarVideo.video
    movieObject.voteAverage = similarVideo.voteAverage
    movieObject.voteCount = similarVideo.voteCount
    return movieObject
}