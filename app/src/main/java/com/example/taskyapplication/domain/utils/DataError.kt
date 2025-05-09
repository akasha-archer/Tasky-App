package com.example.taskyapplication.domain.utils

sealed interface DataError : Error {
    enum class Network : DataError {
        UNAUTHORIZED,
        CONFLICT,
        REQUEST_TIMEOUT,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        TOO_MANY_REQUESTS,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL
    }
}