package com.example.taskyapplication

data class MainViewState (
    val isRegistered: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isValidatingUser: Boolean = false,
)