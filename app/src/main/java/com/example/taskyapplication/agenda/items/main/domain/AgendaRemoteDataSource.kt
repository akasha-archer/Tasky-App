package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgenda
import com.example.taskyapplication.agenda.items.main.domain.network.AgendaApiService
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result
import com.example.taskyapplication.domain.utils.safeApiCall
import retrofit2.Response
import javax.inject.Inject

interface AgendaRemoteDataSource {
    suspend fun getAgendaItemsForDate(
        date: Long
    ): Response<AgendaItemsResponse>
    suspend fun syncAgenda(deletedAgendaItems: DeletedAgendaItems): Result<Unit, DataError.Network>
    suspend fun fetchFullAgenda(fullAgenda: FullAgenda): Result<Unit, DataError.Network>
}

class AgendaItemsRemoteDataSource @Inject constructor(
    private val agendaApiService: AgendaApiService
): AgendaRemoteDataSource {

    override suspend fun getAgendaItemsForDate(date: Long): Response<AgendaItemsResponse> {
        return agendaApiService.getAgendaForDate(date)
    }

    // sync pending deleted items with server
    override suspend fun syncAgenda(
        deletedAgendaItems: DeletedAgendaItems
    ): Result<Unit, DataError.Network> {
        return safeApiCall {
            agendaApiService.syncAgenda(deletedAgendaItems)
        }
    }

    override suspend fun fetchFullAgenda(
        fullAgenda: FullAgenda
    ): Result<Unit, DataError.Network> {
        return safeApiCall {
            agendaApiService.getFullAgenda(fullAgenda)
        }
    }

}