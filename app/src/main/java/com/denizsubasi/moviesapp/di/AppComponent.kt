package com.denizsubasi.moviesapp.di

import android.app.Application
import com.denizsubasi.moviesapp.MoviesApp
import com.denizsubasi.moviesapp.di.modules.AppModule
import com.denizsubasi.moviesapp.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ViewModelModule::class,
            AppModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MoviesApp)
}