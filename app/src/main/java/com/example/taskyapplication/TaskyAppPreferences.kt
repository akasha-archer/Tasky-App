package com.example.taskyapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tasky_preferences")
val AUTH_TOKEN = stringPreferencesKey("auth_token")

class TaskyAppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //write token to store
    suspend fun updateAuthToken(newToken: String) {
        val currentToken = readAuthToken()
        if (currentToken != newToken) {
            context.dataStore.edit { token ->
                token[AUTH_TOKEN] = newToken
            }
        }
    }

    // read token from store
    private suspend fun readAuthToken(): String {
        val preferences = context.dataStore.data.first()
        return preferences[AUTH_TOKEN] ?: "no value found for token"
    }
}