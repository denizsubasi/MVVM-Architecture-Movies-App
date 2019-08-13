package com.denizsubasi.moviesapp.di.modules

import com.denizsubasi.moviesapp.di.scopes.*
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


@Module
class SchedulerModule {

    @Provides
    @ForData
    fun providesNetworkScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @ForDomain
    fun providesDomainScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @Provides
    @ForUi
    fun providesUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @ForUiForeground
    fun providesUiForegroundScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @ForUiBackground
    fun providesUiBackgroundScheduler(): Scheduler {
        return Schedulers.computation()
    }
}
