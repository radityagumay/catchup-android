package com.radityalabs.ui.main.di

import com.google.gson.Gson
import com.radityalabs.helpers.DefaultFileReader
import com.radityalabs.helpers.FileReader
import com.radityalabs.ui.main.data.MainRepository
import com.radityalabs.ui.main.data.impl.DefaultMainRepository
import com.radityalabs.ui.main.data.local.LocalDataSource
import com.radityalabs.ui.main.data.local.impl.DefaultLocalDataSource
import com.radityalabs.ui.main.data.remote.RemoteDataSource
import com.radityalabs.ui.main.data.remote.impl.DefaultRemoteDataSource
import com.radityalabs.ui.main.usecase.MainUseCase
import com.radityalabs.ui.main.usecase.impl.DefaultMainUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.time.ExperimentalTime

@InstallIn(SingletonComponent::class)
@Module
abstract class MainModule {

    @ExperimentalTime
    @Binds
    abstract fun repository(impl: DefaultMainRepository): MainRepository

    @Binds
    abstract fun usecase(impl: DefaultMainUseCase): MainUseCase

    @Binds
    abstract fun localDataSource(impl: DefaultLocalDataSource): LocalDataSource

    @Binds
    abstract fun remoteDataSource(impl: DefaultRemoteDataSource): RemoteDataSource

    @Binds
    abstract fun bindFileReader(impl: DefaultFileReader): FileReader

    companion object {
        @Provides
        fun provideGson(): Gson {
            return Gson()
        }
    }
}
