package com.denizsubasi.moviesapp.di.modules

import com.denizsubasi.moviesapp.ui.flow.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMoviesFragment(): MoviesFragment
}