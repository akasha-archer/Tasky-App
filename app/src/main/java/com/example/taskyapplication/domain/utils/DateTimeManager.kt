package com.example.taskyapplication.domain.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

interface TimeProvider {
    val now: ZonedDateTime
    val nowInstant: Instant
}

data object SystemTimeProvider: TimeProvider {
    override val now: ZonedDateTime
        get() = ZonedDateTime.now(ZoneId.of("UTC"))
    override val nowInstant: Instant
        get() = Instant.now()
}
