package com.denizsubasi.moviesapp.di.modules

import android.app.Application
import androidx.room.Room
import com.denizsubasi.moviesapp.db.MoviesDao
import com.denizsubasi.moviesapp.db.MoviesAppDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
        includes = [UtilityModule::class,
            ActivityModule::class,
            FragmentBuildersModule::class,
            NetworkModule::class,
            SchedulerModule::class,
            ProviderModule::class
        ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): MoviesAppDB {
        return Room
                .databaseBuilder(app, MoviesAppDB::class.java, "MoviesAppDB.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideMostPopularMoviesDao(db: MoviesAppDB): MoviesDao {
        return db.moviesDao()
    }

}

