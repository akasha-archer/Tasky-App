package com.example.taskyapplication.agenda.task.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskyapplication.agenda.task.data.local.entity.PendingTaskEntity

@Dao
interface PendingTaskDao {

    @Upsert
    suspend fun upsertPendingTask(task: PendingTaskEntity)

//    @Query("SELECT * FROM pendingtasks WHERE taskId = :taskId")
//    suspend fun saveTaskToDelete(taskId: String)

    @Query("DELETE FROM pendingtasks WHERE taskId = :taskId")
    suspend fun deletePendingTask(taskId: String)

}