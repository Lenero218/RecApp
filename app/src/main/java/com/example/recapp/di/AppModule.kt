package com.example.recapp.di

import android.content.Context

import com.example.recapp.presentation.MainApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn


import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    @Singleton
    @Provides
    fun provideRandomString(): String{
        return "Hey look a random string!!!!!GNKGNDFK"
    }
}