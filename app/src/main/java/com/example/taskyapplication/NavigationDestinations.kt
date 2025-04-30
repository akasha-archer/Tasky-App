package com.example.taskyapplication

import kotlinx.serialization.Serializable

// navigation screen destinations

// shell for initial screen that selects UI based on
// user's token validity or registration status
@Serializable
object UserStateScreen

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object AgendaScreen

@Serializable
object EventScreen

@Serializable
object TaskScreen

@Serializable
object ReminderScreen






