package com.example.taskyapplication.agenda.items.main.domain

import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgendaResponse
import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult

interface AgendaRepository {
    suspend fun syncDeletedItems(
        deletedAgendaItems: DeletedAgendaItems
    ): EmptyResult<DataError>

    suspend fun fetchFullAgenda(
        fullAgendaResponse: FullAgendaResponse
    ): EmptyResult<DataError>
}