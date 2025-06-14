package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgendaResponse
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import javax.inject.Inject

class AgendaOfflineFirstRepository @Inject constructor(
    private val localDataSource: AgendaLocalDataSource,
    private val remoteDataSource: AgendaRemoteDataSource,
): AgendaRepository {

    override suspend fun syncDeletedItems(deletedAgendaItems: DeletedAgendaItems): EmptyResult<DataError> {
        // check pending list and internet connection
        // if connected, push pending to update deleted items
        TODO("Not yet implemented")
    }



    // fetch from Remote and populate database
    override suspend fun fetchFullAgenda(fullAgendaResponse: FullAgendaResponse): EmptyResult<DataError> {
        TODO("Not yet implemented")
    }

}