package com.example.taskyapplication.auth.domain

import com.example.taskyapplication.remote.TaskyApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val taskyApiService: TaskyApiService
) {
    suspend fun registerNewUser(
        userRegistrationData: NewUserRegistrationData
    ) {

    }
}
