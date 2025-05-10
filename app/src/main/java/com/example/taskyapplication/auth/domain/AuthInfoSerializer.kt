package com.example.taskyapplication.auth.domain

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object AuthInfoSerializer : Serializer<AuthInfo> {
    override val defaultValue: AuthInfo
        get() = AuthInfo()

    override suspend fun readFrom(input: InputStream): AuthInfo {
        return try {
            Json.decodeFromString(
                deserializer = AuthInfo.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AuthInfo, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AuthInfo.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}