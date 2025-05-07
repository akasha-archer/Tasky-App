package com.example.taskyapplication.domain.util

sealed interface DataError: Error {
    data class Network(val type: ErrorType, val message: String? = null) : DataError {
        enum class ErrorType : DataError {
            REQUEST_TIMEOUT,
            UNAUTHORIZED,
            CONFLICT,
            TOO_MANY_REQUESTS,
            NO_INTERNET,
            PAYLOAD_TOO_LARGE,
            SERVER_ERROR,
            SERIALIZATION,
            UNKNOWN
        }

        enum class Local : DataError {
            DISK_FULL
        }
    }
}