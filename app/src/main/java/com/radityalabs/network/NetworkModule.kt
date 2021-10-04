package com.radityalabs.network

import android.content.Context
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    internal fun providesOkHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .cache(Cache(File(context.cacheDir, "http_cache"), 50L * 1024L * 1024L))
            .build()
    }

    @Provides
    @Singleton
    internal fun providesRetrofit(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.get())
            .baseUrl("http://apilayer.net/api/")
            .build()
    }

    @Provides
    @Singleton
    internal fun providesService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }
}
