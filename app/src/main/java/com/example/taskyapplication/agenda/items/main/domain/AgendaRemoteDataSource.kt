package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.domain.network.AgendaApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import javax.inject.Inject

interface AgendaRemoteDataSource {
    suspend fun getAgendaItemsForDate(
        date: Long
    ): Result<AgendaItemsResponse, DataError.Network>
    suspend fun syncAgenda(deletedAgendaItems: DeletedAgendaItems): Result<Unit, DataError.Network>
    suspend fun fetchFullAgenda(): Result<Unit, DataError.Network>
}

class AgendaItemsRemoteDataSource @Inject constructor(
    private val agendaApiService: AgendaApiService
): AgendaRemoteDataSource {

    // fetch for list to display
    override suspend fun getAgendaItemsForDate(date: Long): Result<AgendaItemsResponse, DataError.Network> {
        TODO("Not yet implemented")
    }

    // sync pending deleted items with server
    override suspend fun syncAgenda(
        deletedAgendaItems: DeletedAgendaItems
    ): Result<Unit, DataError.Network> {
        // check pending list and internet connection
        // if connected, push pending to update deleted items
        TODO("Not yet implemented")
    }

    // fetch from Remote and populate database
    // local function gathers and pushes pending items, then this function fetches all
    override suspend fun fetchFullAgenda(): Result<Unit, DataError.Network> {
        TODO("Not yet implemented")
    }

}