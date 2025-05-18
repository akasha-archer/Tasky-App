package com.example.taskyapplication.di

import android.util.Log
import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.AuthRepositoryImpl
import com.example.taskyapplication.auth.domain.AuthTokenManager
import com.example.taskyapplication.MainRepository
import com.example.taskyapplication.MainRepositoryImpl
import com.example.taskyapplication.network.TaskyApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.Response
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
    fun provideOkHttpClient(
        authTokenManager: AuthTokenManager
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authTokenManager))
            .build()
    }

    @Singleton
    @Provides
    fun provideTaskyApi(authTokenManager: AuthTokenManager): TaskyApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideOkHttpClient(authTokenManager))
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(TaskyApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        taskyApiService: TaskyApiService,
        authTokenManager: AuthTokenManager
    ): AuthRepository =
        AuthRepositoryImpl(
            taskyApiService,
            authTokenManager
        )

    @Singleton
    @Provides
    fun provideMainRepository(
        taskyApiService: TaskyApiService
    ): MainRepository =
        MainRepositoryImpl(
            taskyApiService
        )
}

class AuthInterceptor(val authTokenManager: AuthTokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val token = runBlocking {
            authTokenManager.readAccessToken()
        }
        val newRequest = request.newBuilder()
            .header("x-api-key", BuildConfig.API_KEY)
            .header("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(newRequest)
        when (response.code) {
            200 -> {
                Log.d("Tasky API 200 response", "$response")
            }
            400, 401, 403, 404 -> {
                Log.e("Tasky API ${response.code} error", "$response")
            }
            in 500..599 -> {
                Log.e("Tasky API server error: ${response.code}", "$response")
            }
        }
        return response
    }
}
