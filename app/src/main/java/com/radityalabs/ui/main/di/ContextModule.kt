package com.radityalabs.ui.main.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ContextModule {
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }
}