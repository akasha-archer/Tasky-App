package com.example.taskyapplication

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.taskyapplication.di.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskyAppPreferences @Inject constructor(
    private val context: Context
) {
    // Cache for the token
    private var cachedToken: String = ""

    //write token to store
    suspend fun saveAuthToken(newToken: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = newToken
            cachedToken = newToken  // Update cache when token is set
        }
    }

    // Suspend function to update the cache from DataStore
    suspend fun refreshAuthToken() {
        context.dataStore.data.collect { preferences ->
            cachedToken = preferences[AUTH_TOKEN] ?: "no value found for token"
        }
    }

    // Suspend function to read and write to DataStore
    suspend fun readAuthToken(): String {
        return context.dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN] ?: "no value found for token"
        }.first()
    }

    // Non-suspend function for the auth interceptor to use
    fun getCachedAuthToken(): String = cachedToken

    companion object {
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
    }
}
