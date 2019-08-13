package com.denizsubasi.moviesapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UtilityModule {

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }


}