package com.example.taskyapplication.auth.register

sealed interface RegisterAction {
     data object OnLoginClick: RegisterAction
    data object OnRegisterClick: RegisterAction
}