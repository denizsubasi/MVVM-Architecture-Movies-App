package com.denizsubasi.moviesapp.di.modules

import androidx.lifecycle.ViewModel
import com.denizsubasi.moviesapp.di.ViewModelKey
import com.denizsubasi.moviesapp.ui.flow.movies.MoviesViewModel
import com.denizsubasi.moviesapp.ui.flow.moviesdetails.MovieDetailsViewModel
import com.denizsubasi.moviesapp.ui.flow.moviesdetails.playerflow.VideoPlayerViewModel
import com.denizsubasi.moviesapp.ui.flow.specificcategorymovielistflow.SpecificCategoryMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindTrailersViewModel(userViewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SpecificCategoryMoviesViewModel::class)
    abstract fun bindSpecificCategoryMoviesViewModel(userViewModel: SpecificCategoryMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoPlayerViewModel::class)
    abstract fun bindVideoPlayerViewModel(userViewModel: VideoPlayerViewModel): ViewModel


}