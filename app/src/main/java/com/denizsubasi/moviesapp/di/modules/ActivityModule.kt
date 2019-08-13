package com.denizsubasi.moviesapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.denizsubasi.moviesapp.di.ViewModelFactory
import com.denizsubasi.moviesapp.ui.flow.MainActivity
import com.denizsubasi.moviesapp.ui.flow.moviesdetails.MoviesDetailsActivity
import com.denizsubasi.moviesapp.ui.flow.moviesdetails.playerflow.VideoPlayerActivity
import com.denizsubasi.moviesapp.ui.flow.specificcategorymovielistflow.SpecificCategoryMoviesActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeSpecificCategoryMoviesActivity(): SpecificCategoryMoviesActivity


    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMovieDetailsActivity(): MoviesDetailsActivity

    @ContributesAndroidInjector
    abstract fun contributeVideoPlayerActivity(): VideoPlayerActivity

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}