package com.example.taskyapplication.domain.util

import com.example.taskyapplication.auth.data.ErrorResponse
import com.google.gson.JsonParseException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

typealias EmptyResult<E> = Result<Unit, E>

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: com.example.taskyapplication.domain.util.Error>(val error: E):
        Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

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
//
            // Return a detailed error message if available
            if (messages.isNotEmpty()) {
                Result.Error(DataError.Network(DataError.Network.ErrorType.UNKNOWN, messages))
            } else {
                Result.Error(DataError.Network(DataError.Network.ErrorType.UNKNOWN))
            }
        } else {
            // If the response is not successful and there's no error body, return a generic error
            Result.Error(DataError.Network(DataError.Network.ErrorType.UNKNOWN))
        }
    } catch (e: IOException) {
        // Handle network connection issues
        Result.Error(DataError.Network(DataError.Network.ErrorType.NO_INTERNET))
    } catch (e: SocketTimeoutException) {
        // Handle request timeout
        Result.Error(DataError.Network(DataError.Network.ErrorType.REQUEST_TIMEOUT))
    } catch (e: HttpException) {
        // Handle different HTTP exceptions
        when (e.code()) {
            408 -> Result.Error(DataError.Network(DataError.Network.ErrorType.REQUEST_TIMEOUT))
            413 -> Result.Error(DataError.Network(DataError.Network.ErrorType.PAYLOAD_TOO_LARGE))
            401 -> Result.Error(DataError.Network(DataError.Network.ErrorType.SERVER_ERROR))
            in 500..599 -> Result.Error(DataError.Network(DataError.Network.ErrorType.SERVER_ERROR))
            else -> Result.Error(DataError.Network(DataError.Network.ErrorType.UNKNOWN))
        }
    } catch (e: JsonParseException) {
        // Handle JSON parsing errors
        Result.Error(DataError.Network(DataError.Network.ErrorType.SERIALIZATION))
    } catch (e: Exception) {
        // Catch any other exceptions and return as an unknown error
        Result.Error(
            DataError.Network(
                DataError.Network.ErrorType.UNKNOWN,
                e.message.toString()
            )
        )
    }
}