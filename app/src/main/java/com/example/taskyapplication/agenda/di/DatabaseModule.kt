package com.example.taskyapplication.agenda.di

import android.content.Context
import androidx.room.Room
import com.example.taskyapplication.BuildConfig
import com.example.taskyapplication.agenda.data.db.AgendaDatabase
import com.example.taskyapplication.agenda.task.data.OfflineFirstTaskRepository
import com.example.taskyapplication.agenda.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.task.domain.RemoteDataSource
import com.example.taskyapplication.agenda.task.domain.TaskLocalDataSource
import com.example.taskyapplication.agenda.task.domain.TaskRemoteDataSource
import com.example.taskyapplication.agenda.task.domain.TaskRepository
import com.example.taskyapplication.agenda.task.domain.network.TaskApiService
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
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

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
    fun provideLocalDataSource(
        dao: TaskDao
    ): LocalDataSource = TaskLocalDataSource(dao)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        apiService: TaskApiService
    ): RemoteDataSource = TaskRemoteDataSource(apiService)

    @Singleton // Provide always the same instance
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

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
            scope
        )

    @Provides
    @Singleton
    fun provideTaskDao(database: AgendaDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): AgendaDatabase {
        return Room.databaseBuilder(
                context = context,
                klass = AgendaDatabase::class.java,
                name = "task_database"
            ).fallbackToDestructiveMigration(false)
            .build()
    }
}
