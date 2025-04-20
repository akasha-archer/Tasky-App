package com.example.taskyapplication.di

import android.content.res.Resources
import com.example.taskyapplication.R
import com.example.taskyapplication.remote.TaskyApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Singleton

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideKotlinXRetrofitBuilder(
        client: Lazy<OkHttpClient>,
        resources: Resources
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
    }

    @Singleton
    @Provides
    fun provideTaskyApi(
        retrofitBuilder: Retrofit.Builder,
        res: Resources
    ): TaskyApiService =
        retrofitBuilder
            .baseUrl(res.getString(R.string.api_base_url))
            .build()
            .create(TaskyApiService::class.java)

}