package com.example.taskyapplication.auth.login

sealed interface LoginAction {
    data object OnLoginClick: LoginAction
    data object OnSignUpClick: LoginAction
}
