package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgendaResponse
import com.example.taskyapplication.agenda.items.main.domain.network.AgendaApiService
import retrofit2.Response
import javax.inject.Inject

interface AgendaRemoteDataSource {

//    suspend fun syncLocalItemsWithRemoteStorage(): Result<Unit, DataError.Network>
    suspend fun getAgendaItemsForDate(
        date: Long
    ): Response<AgendaItemsResponse>
    suspend fun syncDeletedAgendaItems(deletedAgendaItems: DeletedAgendaItems): Response<Unit>
    suspend fun fetchFullAgenda(): Response<FullAgendaResponse>
}

class AgendaItemsRemoteDataSource @Inject constructor(
    private val agendaApiService: AgendaApiService
): AgendaRemoteDataSource {
    override suspend fun getAgendaItemsForDate(date: Long): Response<AgendaItemsResponse> {
        return agendaApiService.getAgendaForDate(date)
    }

    // sync pending deleted items with server
    override suspend fun syncDeletedAgendaItems(
        deletedAgendaItems: DeletedAgendaItems
    ): Response<Unit> {
        return agendaApiService.syncDeletedAgendaIds(deletedAgendaItems)

    }

    override suspend fun fetchFullAgenda(): Response<FullAgendaResponse> {
        return agendaApiService.getFullAgenda()
    }

}