package com.example.taskyapplication.di

import android.util.Log
import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.AuthRepositoryImpl
import com.example.taskyapplication.auth.domain.AuthTokenManager
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
        tokenManager: AuthTokenManager,

    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()
    }

    @Singleton
    @Provides
    fun provideTaskyApi(tokenManager: AuthTokenManager): TaskyApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideOkHttpClient(tokenManager))
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
}

class AuthInterceptor(private val tokenManager: AuthTokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val accessToken = runBlocking {
            tokenManager.readAccessToken()
        }
        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .header("x-api-key", BuildConfig.API_KEY)
            .build()

        val response = chain.proceed(newRequest)
        when (response.code) {
            200 -> {
                Log.d("Tasky API 200 response", "$response")
            }
            400, 401, 403, 404 -> {
                Log.e("Tasky API ${response.code} error", "$response")
            }
        }
        return response
    }
}
