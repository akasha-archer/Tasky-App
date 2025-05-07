package com.example.taskyapplication.domain.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    // we'll use this function in all
// repos to handle api errors.
    suspend fun <T> executeApiCall(apiToBeCalled: suspend () -> Response<T>): NetworkResult<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    NetworkResult.Success(data = response.body()!!)
                } else {
                    NetworkResult.Error(errorMessage = response.message() ?: "Something went wrong")
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                NetworkResult.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                NetworkResult.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                NetworkResult.Error(errorMessage = "Something went wrong")
            }
        }
    }
}