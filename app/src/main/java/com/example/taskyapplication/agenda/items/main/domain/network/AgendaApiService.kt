package com.example.taskyapplication.agenda.items.main.domain.network

import com.example.taskyapplication.agenda.items.main.data.AgendaItemsResponse
import com.example.taskyapplication.agenda.items.main.data.DeletedAgendaItems
import com.example.taskyapplication.agenda.items.main.data.FullAgenda
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AgendaApiService {
    @GET("/agenda/{selected_date}")
    suspend fun getAgendaForDate(
        @Query("selected_date") date: Long
    ): Response<AgendaItemsResponse>

    @POST("/syncAgenda")
    suspend fun syncAgenda(
        @Body deletedAgendaItems: DeletedAgendaItems
    ): Response<Unit>

    @GET("/fullAgenda")
    suspend fun getFullAgenda(
        @Body fullAgenda: FullAgenda
    ): Response<Unit>
}