package com.example.taskyapplication.agenda.items.event.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskyapplication.agenda.items.event.data.GetAttendeeResponse
import com.example.taskyapplication.agenda.items.event.data.VerifyAttendeeResponse

@Entity(tableName = "attendees")
data class AttendeeEntity(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val email: String
)

fun VerifyAttendeeResponse.asAttendeeEntity() = AttendeeEntity(
    id = userId,
    fullName = fullName,
    email = email,
)
