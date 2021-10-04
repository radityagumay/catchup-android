package com.radityalabs.ui.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object SchedulerModule {
    @Provides
    fun provideScheduler(): Scheduler {
        return object : Scheduler {
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
        }
    }
}

interface Scheduler {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}