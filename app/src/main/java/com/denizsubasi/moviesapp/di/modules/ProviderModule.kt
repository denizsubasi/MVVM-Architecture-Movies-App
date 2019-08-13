package com.denizsubasi.moviesapp.di.modules

import com.denizsubasi.moviesapp.data.MoviesProvider
import com.denizsubasi.moviesapp.domain.contract.MoviesContract
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProviderModule {
    @Provides
    @Singleton
    fun providesMoviesProvider(moviesProvider: MoviesProvider): MoviesContract {
        return moviesProvider
    }
}