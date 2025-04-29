package com.example.taskyapplication.auth.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.taskyapplication.di.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskyAppPreferences @Inject constructor(
    private val context: Context
) {

    // Saving and retrieving user's registration status
    suspend fun saveUserRegisteredState(isRegistered: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[REGISTERED_USER_STATE] = isRegistered
        }
    }

    suspend fun fetchUserRegisteredState(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[REGISTERED_USER_STATE] ?: false
        }.first()
    }

    // manage tokens with data store
    suspend fun saveAccessToken(newToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = newToken
        }
    }

    suspend fun readAccessToken(): String {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN] ?: ""
        }.first()
    }

    suspend fun saveRefreshToken(newToken: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = newToken
        }
    }

    suspend fun readRefreshToken(): String {
        return context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN] ?: ""
        }.first()
    }

    suspend fun deleteRefreshToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(REFRESH_TOKEN)
        }
    }



    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val REGISTERED_USER_STATE = booleanPreferencesKey("user_registered_state")
    }
}
