package com.example.taskyapplication.di

import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.auth.domain.AuthRepository
import com.example.taskyapplication.auth.domain.AuthRepositoryImpl
import com.example.taskyapplication.auth.domain.AuthTokenManager
import com.example.taskyapplication.MainRepository
import com.example.taskyapplication.MainRepositoryImpl
import com.example.taskyapplication.network.AuthApiService
import com.example.taskyapplication.network.CommonInterceptor
import com.example.taskyapplication.network.RefreshAuthenticationInterceptor
import com.example.taskyapplication.network.TokenRefreshApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

   /**
    *
    * Add any interceptors specific to the refresh call, e.g., API key if needed
    *  but NOT AuthInterceptor or RefreshAuthenticationInterceptor
    *
   * */
    @Provides
    @Singleton
    @Named("RefreshClient") // Qualifier for the refresh API's OkHttpClient
    fun provideRefreshOkHttpClient(
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenRefreshApi(
        @Named("RefreshClient") refreshOkHttpClient: OkHttpClient // Use the dedicated client
    ): TokenRefreshApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(refreshOkHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(TokenRefreshApi::class.java)
    }

    /**
     * Retrofit builder for all APIs except the TokenRefreshApi
     * */
    @Provides
    @Singleton
    @Named("AuthenticatedClient")
    fun provideMainRetrofit(
        @Named("AuthenticatedClient") authenticatedOkHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(authenticatedOkHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    /**
     * Client/ Interceptor for all API calls except the Token Refresh
     * */
    @Provides
    @Singleton
    @Named("AuthenticatedClient") // Qualifier for the main API's OkHttpClient
    fun provideAuthenticatedOkHttpClient(
        commonInterceptor: CommonInterceptor,
        refreshAuthenticationInterceptor: RefreshAuthenticationInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(commonInterceptor) // Adds API key and current access token
            .addInterceptor(refreshAuthenticationInterceptor) // Handles 401s and refreshes
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRefreshAuthenticationInterceptor(
        authTokenManager: AuthTokenManager,
        tokenRefreshApiProvider: Provider<TokenRefreshApi> // Hilt will provide the Provider
    ): RefreshAuthenticationInterceptor {
        return RefreshAuthenticationInterceptor(authTokenManager, tokenRefreshApiProvider)
    }

    @Provides
    @Singleton
    fun provideCommonInterceptor(authTokenManager: AuthTokenManager): CommonInterceptor {
        return CommonInterceptor(authTokenManager)
    }

    @Singleton
    @Provides
    fun provideTaskyApi(
        @Named("AuthenticatedClient") retrofit: Retrofit,
        authTokenManager: AuthTokenManager,
        ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authApiService: AuthApiService,
        authTokenManager: AuthTokenManager,
        tokenRefreshApi: TokenRefreshApi
    ): AuthRepository =
        AuthRepositoryImpl(
            authApiService,
            tokenRefreshApi,
            authTokenManager
        )

    @Singleton
    @Provides
    fun provideMainRepository(
        authApiService: AuthApiService
    ): MainRepository =
        MainRepositoryImpl(
            authApiService
        )
}
