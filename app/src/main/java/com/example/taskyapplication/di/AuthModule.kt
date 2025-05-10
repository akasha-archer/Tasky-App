package com.example.taskyapplication.di

import android.content.Context
import com.example.taskyapplication.auth.data.EmailPatternValidator
import com.example.taskyapplication.auth.domain.AuthTokenManager
import com.example.taskyapplication.auth.domain.AuthTokenManagerImpl
import com.example.taskyapplication.auth.domain.PatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Singleton
    @Provides
    fun providePatternValidator(): PatternValidator = EmailPatternValidator

    @Singleton
    @Provides
    fun provideAuthTokenManager(@ApplicationContext context: Context): AuthTokenManager = AuthTokenManagerImpl(context)
}

