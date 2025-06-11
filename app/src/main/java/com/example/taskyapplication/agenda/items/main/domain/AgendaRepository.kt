package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgenda
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import retrofit2.Response

interface AgendaRepository {
    suspend fun getAgendaItemsForDate(
        date: Long,
    ): Response<AgendaItemsResponse>

    suspend fun syncDeletedItems(
        deletedAgendaItems: DeletedAgendaItems
    ): EmptyResult<DataError>

    suspend fun fetchFullAgenda(
        fullAgenda: FullAgenda
    ): EmptyResult<DataError>
}