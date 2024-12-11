package com.example.sportassistant.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserPreferencesRepository(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "layout_preferences"
    )

    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .catch{
            if(it is IOException) {
                Log.e("UserPreferencesRepo", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    suspend fun saveLayoutPreference(theme: Boolean) = withContext(Dispatchers.IO) {
        context.dataStore.edit {preferences ->
            preferences[IS_DARK_THEME] = theme
        }
    }
}