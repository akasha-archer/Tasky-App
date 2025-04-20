package com.example.taskyapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tasky_preferences")
val AUTH_TOKEN = stringPreferencesKey("auth_token")

class TaskyAppPreferences @Inject constructor(
    private val context: Context
) {
    //write to store
    suspend fun updateAuthToken(newToken: String) {
        context.dataStore.edit { token ->
            token[AUTH_TOKEN] = newToken
        }
    }

    // read from store
    suspend fun readAuthToken(): String {
        val preferences = context.dataStore.data.first()
        return preferences[AUTH_TOKEN] ?: "no value found for token"
    }
}