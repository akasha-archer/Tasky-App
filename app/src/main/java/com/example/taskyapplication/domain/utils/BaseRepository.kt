package com.example.taskyapplication.domain.utils

import com.example.taskyapplication.auth.data.ErrorResponse
import com.google.gson.JsonParseException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository {

    // code reference: https://arundevofficial.medium.com/efficient-networking-in-kotlin-handling-api-responses-gracefully-ba1a3a3becdf
    suspend fun <T : Any> executeApi(call: suspend () -> Response<T>): Result<T, DataError.Network> {
        return try {
            // Invoke the API call and get the response
            val response = call.invoke()
            val body = response.body()
            val errorBody = response.errorBody()

            if (response.isSuccessful && body != null) {
                // If the response is successful and contains a body, return it as a success
                Result.Success(body)
            } else if (errorBody != null) {
                // Handle error response
                val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody.string())
                val messages = errorResponse.message
                // Return a detailed error message if available
                if (messages.isNotEmpty()) {
                    Result.Error(DataError.Network.UNKNOWN)
                } else {
                    Result.Error(DataError.Network.UNKNOWN)
                }
            } else {
                // If the response is not successful and there's no error body, return a generic error
                Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: IOException) {
            // Handle network connection issues
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: SocketTimeoutException) {
            // Handle request timeout
            Result.Error(DataError.Network.REQUEST_TIMEOUT)
        } catch (e: HttpException) {
            // Handle different HTTP exceptions
            when (e.code()) {
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                401 -> Result.Error(DataError.Network.SERVER_ERROR)
                in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        } catch (e: JsonParseException) {
            // Handle JSON parsing errors
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            // Catch any other exceptions and return as an unknown error
            Result.Error(
                DataError.Network.UNKNOWN
            )
        }
    }
}
