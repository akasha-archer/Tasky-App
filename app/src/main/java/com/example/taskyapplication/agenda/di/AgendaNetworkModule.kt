package com.example.taskyapplication.agenda.di

import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderLocalDataSource
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderOfflineFirstRepository
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRemoteDataSource
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRemoteDataSourceImpl
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.items.reminder.domain.network.ReminderApiService
import com.example.taskyapplication.agenda.items.task.data.OfflineFirstTaskRepository
import com.example.taskyapplication.agenda.items.task.data.local.dao.PendingTaskDao
import com.example.taskyapplication.agenda.items.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRemoteDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.agenda.items.task.domain.network.TaskApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AgendaNetworkModule {

    // CORE FUNCTIONS
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideAgendaOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton // Provide always the same instance
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    // REMINDERS
    @Singleton
    @Provides
    fun provideReminderApi(): ReminderApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideAgendaOkHttpClient())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(ReminderApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideReminderRemoteDataSource(
        apiService: ReminderApiService
    ): ReminderRemoteDataSource = ReminderRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideReminderRepository(
        localDataSource: ReminderLocalDataSource,
        remoteDataSource: ReminderRemoteDataSource,
        scope: CoroutineScope
    ): ReminderRepository =
        ReminderOfflineFirstRepository(
            localDataSource,
            remoteDataSource,
            scope
        )

    // TASKS
    @Singleton
    @Provides
    fun provideTaskApi(): TaskApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideAgendaOkHttpClient())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(TaskApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTaskRemoteDataSource(
        apiService: TaskApiService
    ): RemoteDataSource = TaskRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideTaskRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        pendingTaskDao: PendingTaskDao,
        scope: CoroutineScope
    ): TaskRepository =
        OfflineFirstTaskRepository(
            localDataSource,
            remoteDataSource,
            pendingTaskDao,
            scope
        )
}
