package com.example.sportassistant.data.repository

import android.content.Context
import android.provider.Settings.Secure
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sportassistant.dataStore
import com.example.sportassistant.domain.model.AppDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserPreferencesRepository(
    private val context: Context,
    val appDispatchers: AppDispatchers,
) {

    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val IS_FILLED_PROFILE = booleanPreferencesKey("is_filled_profile")
        val SID = stringPreferencesKey("sid")
    }

    fun getSID(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[SID] ?: ""
        }
    }

    fun isDarkTheme(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }
    }

    fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    fun isFilledProfile(): Flow<Boolean?> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_FILLED_PROFILE] ?: null
        }
    }

    suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        withContext(appDispatchers.io) {
            context.dataStore.edit {preferences ->
                preferences[IS_LOGGED_IN] = isLoggedIn
            }
        }
    }

    suspend fun setIsProfileFilled(isProfileFilled: Boolean) {
        withContext(appDispatchers.io) {
            context.dataStore.edit {preferences ->
                preferences[IS_FILLED_PROFILE] = isProfileFilled
            }
        }
    }

    suspend fun saveLayoutPreference(theme: Boolean) {
        withContext(appDispatchers.io) {
            context.dataStore.edit {preferences ->
                preferences[IS_DARK_THEME] = theme
            }
        }
    }

    suspend fun saveSID(sid: String) {
        withContext(appDispatchers.io) {
            context.dataStore.edit {preferences ->
                preferences[SID] = sid
            }
        }
    }
}