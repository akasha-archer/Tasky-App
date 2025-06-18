package com.example.taskyapplication.domain.utils

import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

const val SUCCESS_CODE = 200
suspend inline fun <reified T> safeApiCall(execute: () -> Response<T>): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.REQUEST_TIMEOUT)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
    return responseToResult(response)
}

inline fun <reified T> responseToResult(response: Response<T>): Result<T, DataError.Network> {
    return when(response.code()) {
        in 200..299 -> if (response.body() != null) Result.Success(response.body()!!) else Result.Error(DataError.Network.UNKNOWN)
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}
