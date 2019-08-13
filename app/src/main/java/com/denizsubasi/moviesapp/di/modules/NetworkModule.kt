package com.denizsubasi.moviesapp.di.modules

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.denizsubasi.moviesapp.data.api.RestApi
import com.denizsubasi.moviesapp.util.ApplicationConstants.Companion.BASE_URL
import com.denizsubasi.moviesapp.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingCapableHttpClient(logLevel: HttpLoggingInterceptor.Level):
            OkHttpClient {
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.tag("retrofit").d(message)
        })
        logging.level = logLevel
        return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
            okHttpClient: OkHttpClient,
            gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideRestService(
            retrofitBuilder: Retrofit.Builder
    ): RestApi {
        return retrofitBuilder.baseUrl(BASE_URL)
                .build()
                .create<RestApi>(RestApi::class.java)
    }


    @Provides
    @Singleton
    fun provideGsonConverter(): Gson {
        return GsonBuilder().serializeNulls().create()
    }


    @Provides
    fun provideServiceLogLevel(): HttpLoggingInterceptor.Level {
        return HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesRequestManager(application: Application): RequestManager {
        return Glide.with(application)
                .setDefaultRequestOptions(
                        (RequestOptions.diskCacheStrategyOf(
                                DiskCacheStrategy.ALL
                        ))
                )
    }

}