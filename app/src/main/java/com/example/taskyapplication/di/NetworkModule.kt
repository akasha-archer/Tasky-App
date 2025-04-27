package com.example.taskyapplication.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.auth.data.TaskyAppPreferences
import com.example.taskyapplication.auth.data.TokenAuthenticator
import com.example.taskyapplication.remote.TaskyApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tasky_preferences")

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthenticator(appPreferences: TaskyAppPreferences): Authenticator = TokenAuthenticator(appPreferences)

    @Singleton
    @Provides
    fun provideTaskyAppPreferences(@ApplicationContext context: Context): TaskyAppPreferences = TaskyAppPreferences(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(appPreferences: TaskyAppPreferences, authenticator: TokenAuthenticator): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(AuthInterceptor(appPreferences))
            .build()
    }

    @Singleton
    @Provides
    fun provideTaskyApi(appPreferences: TaskyAppPreferences, authenticator: TokenAuthenticator): TaskyApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideOkHttpClient(appPreferences, authenticator))
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(TaskyApiService::class.java)
    }
}

class AuthInterceptor(private val taskyAppPreferences: TaskyAppPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer ${taskyAppPreferences.getCachedAuthToken()}")
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
