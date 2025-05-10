package com.example.taskyapplication

import com.example.taskyapplication.domain.utils.DataError
import com.example.taskyapplication.domain.utils.EmptyResult
import com.example.taskyapplication.domain.utils.asEmptyDataResult
import com.example.taskyapplication.domain.utils.safeApiCall
import com.example.taskyapplication.network.TaskyApiService
import javax.inject.Inject

interface MainRepository {
    suspend fun authenticateToken(): EmptyResult<DataError>
}

class MainRepositoryImpl @Inject constructor(
    private val taskyApiService: TaskyApiService
) : MainRepository {

    override suspend fun authenticateToken(): EmptyResult<DataError> {
        val result = safeApiCall {
            taskyApiService.authenticateUser()
        }
        return result.asEmptyDataResult()
    }

}