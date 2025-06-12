package com.example.taskyapplication.agenda.di

import android.content.Context
import androidx.room.Room
import com.example.taskyapplication.agenda.data.db.AgendaDatabase
import com.example.taskyapplication.agenda.items.event.data.db.EventDao
import com.example.taskyapplication.agenda.items.event.domain.EventLocalDataSource
import com.example.taskyapplication.agenda.items.event.domain.EventLocalDataSourceImpl
import com.example.taskyapplication.agenda.items.main.data.db.LocalAgendaSummaryDao
import com.example.taskyapplication.agenda.items.main.domain.AgendaItemsLocalDataSource
import com.example.taskyapplication.agenda.items.main.domain.AgendaLocalDataSource
import com.example.taskyapplication.agenda.items.reminder.data.db.ReminderDao
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderLocalDataSource
import com.example.taskyapplication.agenda.items.reminder.domain.ReminderLocalDataSourceImpl
import com.example.taskyapplication.agenda.items.task.data.local.dao.PendingTaskDao
import com.example.taskyapplication.agenda.items.task.data.local.dao.TaskDao
import com.example.taskyapplication.agenda.items.task.domain.LocalDataSource
import com.example.taskyapplication.agenda.items.task.domain.TaskLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    // AGENDA SCREEN
    @Singleton
    @Provides
    fun provideAgendaSummaryDao(database: AgendaDatabase): LocalAgendaSummaryDao {
        return database.localAgendaSummaryDao()
    }

    @Singleton
    @Provides
    fun provideAgendaLocalDataSource(
        dao: LocalAgendaSummaryDao
    ): AgendaLocalDataSource = AgendaItemsLocalDataSource(dao)

    // EVENT
    @Singleton
    @Provides
    fun provideEventDao(database: AgendaDatabase): EventDao {
        return database.eventDao()
    }

    @Singleton
    @Provides
    fun provideEventLocalDataSource(
        dao: EventDao
    ): EventLocalDataSource = EventLocalDataSourceImpl(dao)

    // REMINDERS
    @Singleton
    @Provides
    fun provideReminderDao(database: AgendaDatabase): ReminderDao {
        return database.reminderDao()
    }

    @Singleton
    @Provides
    fun provideReminderLocalDataSource(
        dao: ReminderDao
    ): ReminderLocalDataSource = ReminderLocalDataSourceImpl(dao)

// TASKS
    @Singleton
    @Provides
    fun provideLocalDataSource(
        dao: TaskDao
    ): LocalDataSource = TaskLocalDataSource(dao)

    @Provides
    @Singleton
    fun providePendingTaskDao(database: AgendaDatabase): PendingTaskDao {
        return database.pendingTaskDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: AgendaDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideAgendaDatabase(@ApplicationContext context: Context): AgendaDatabase {
        return Room.databaseBuilder(
                context = context,
                klass = AgendaDatabase::class.java,
                name = "task_database"
            ).fallbackToDestructiveMigration(false)
            .build()
    }
}
