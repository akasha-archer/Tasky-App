package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.AgendaSummary
import com.example.taskyapplication.agenda.items.main.data.LocalAgendaSummary
import com.example.taskyapplication.agenda.items.main.data.db.LocalAgendaSummaryDao
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import javax.inject.Inject

interface AgendaLocalDataSource {
    suspend fun getAgendaForDate(
        date: Long
    ): Result<LocalAgendaSummary, DataError.Local>
    suspend fun syncDeletedAgendaItems(): Result<Unit, DataError.Local>
    suspend fun getPendingAgendaItems(): Result<Unit, DataError.Local>
}

class AgendaItemsLocalDataSource @Inject constructor(
localAgendaSummaryDao: LocalAgendaSummaryDao
): AgendaLocalDataSource {
    override suspend fun getAgendaForDate(
        date: Long
    ): Result<LocalAgendaSummary, DataError.Local> {
        TODO("Not yet implemented")
    }
    // return LocalAgendaSummary > use entities as properties

    // insert pending deleted items from failed delete in ViewModel
    override suspend fun syncDeletedAgendaItems(): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    // collect pending items for syncing to remote
    override suspend fun getPendingAgendaItems(): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

}