package com.example.taskyapplication.agenda.di

import android.content.Context
import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.agenda.common.INetworkObserver
import com.example.taskyapplication.agenda.common.NetworkStatusObserver
import com.example.taskyapplication.agenda.items.event.data.EventLocalDataSource
import com.example.taskyapplication.agenda.items.event.domain.EventOfflineFirstRepository
import com.example.taskyapplication.agenda.items.event.data.EventRemoteDataSource
import com.example.taskyapplication.agenda.items.event.data.EventRemoteDataSourceImpl
import com.example.taskyapplication.agenda.items.event.domain.EventRepository
import com.example.taskyapplication.agenda.items.event.domain.ImageMultiPartProvider
import com.example.taskyapplication.agenda.items.event.network.EventApiService
import com.example.taskyapplication.agenda.items.main.domain.AgendaItemsRemoteDataSource
import com.example.taskyapplication.agenda.items.main.domain.AgendaRemoteDataSource
import com.example.taskyapplication.agenda.items.main.domain.network.AgendaApiService
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderLocalDataSource
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderOfflineFirstRepository
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRemoteDataSource
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRemoteDataSourceImpl
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderRepository
import com.example.taskyapplication.agenda.items.reminder.domain.network.ReminderApiService
import com.example.taskyapplication.agenda.items.task.data.OfflineFirstTaskRepository
import com.example.taskyapplication.agenda.items.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRemoteDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskRepository
import com.example.taskyapplication.agenda.items.task.domain.network.TaskApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
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
    fun provideNetworkObserver(
        @ApplicationContext context: Context
    ): INetworkObserver =
        NetworkStatusObserver(context)

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

    // AGENDA SCREEN
    @Singleton
    @Provides
    fun provideAgendaApi(): AgendaApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideAgendaOkHttpClient())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(AgendaApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAgendaRemoteDataSource(
        apiService: AgendaApiService
    ): AgendaRemoteDataSource = AgendaItemsRemoteDataSource(apiService)

    // EVENTS
    @Singleton
    @Provides
    fun provideEventApi(
        @Named("AuthenticatedClient") retrofit: Retrofit
    ): EventApiService {
        return retrofit.create(EventApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideEventRemoteDataSource(
        apiService: EventApiService
    ): EventRemoteDataSource = EventRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideEventRepository(
        localDataSource: EventLocalDataSource,
        remoteDataSource: EventRemoteDataSource,
        imageMultiPartProvider: ImageMultiPartProvider,
        scope: CoroutineScope,
        @ApplicationContext applicationContext: Context
    ): EventRepository =
        EventOfflineFirstRepository(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            applicationScope = scope,
            applicationContext = applicationContext,
            imageMultiPartProvider = imageMultiPartProvider
        )

    // REMINDERS
    @Singleton
    @Provides
    fun provideReminderApi(
        @Named("AuthenticatedClient") retrofit: Retrofit
    ): ReminderApiService {
        return retrofit.create(ReminderApiService::class.java)
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
    fun provideTaskApi(
        @Named("AuthenticatedClient") retrofit: Retrofit
    ): TaskApiService {
        return retrofit.create(TaskApiService::class.java)
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
        scope: CoroutineScope
    ): TaskRepository =
        OfflineFirstTaskRepository(
            localDataSource,
            remoteDataSource,
            scope,
        )
}
