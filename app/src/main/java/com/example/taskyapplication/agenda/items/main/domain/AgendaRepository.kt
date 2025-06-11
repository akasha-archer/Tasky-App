package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgenda
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.Result

interface AgendaRepository {
    suspend fun getAgendaItemsForDate(
        date: Long,
    ): Result<AgendaItemsResponse, DataError.Network>

    suspend fun syncDeletedItems(
        deletedAgendaItems: DeletedAgendaItems
    ): Result<Unit, DataError.Network>

    suspend fun fetchFullAgenda(
        fullAgenda: FullAgenda
    ): Result<Unit, DataError.Network>
}