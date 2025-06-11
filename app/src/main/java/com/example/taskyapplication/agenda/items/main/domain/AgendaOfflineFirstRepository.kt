package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgenda
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class AgendaOfflineFirstRepository @Inject constructor(
    private val localDataSource: AgendaLocalDataSource,
    private val remoteDataSource: AgendaRemoteDataSource,
    private val applicationScope: CoroutineScope,
): AgendaRepository {

    override suspend fun getAgendaItemsForDate(date: Long): Result<AgendaItemsResponse, DataError.Network> {
       //  fetch from remote
        // if success -> insert to database as summary entities
        // if failure -> error message
        TODO("Not yet implemented")
    }

    override suspend fun syncDeletedItems(deletedAgendaItems: DeletedAgendaItems): Result<Unit, DataError.Network> {
        //
        TODO("Not yet implemented")
    }

    override suspend fun fetchFullAgenda(fullAgenda: FullAgenda): Result<Unit, DataError.Network> {
        TODO("Not yet implemented")
    }

}