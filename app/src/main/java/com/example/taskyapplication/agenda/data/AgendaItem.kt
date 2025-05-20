package com.example.taskyapplication.agenda.data

interface AgendaItem {
    val eventId: String
    val title: String
    val description: String
    val startTime: Long
    val reminderTime: Long
}