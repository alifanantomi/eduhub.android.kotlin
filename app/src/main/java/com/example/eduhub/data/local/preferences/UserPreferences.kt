package com.example.eduhub.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.eduhub.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "eduhub_preferences")

@Singleton
class
UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private var cachedAuthToken: String? = null

    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_key")
        val USER_DATA = stringPreferencesKey("user_data")
    }

    val authTokenFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] ?: ""
        }

    val userFlow: Flow<User?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_DATA]?.let {
                try {
                    gson.fromJson(it, User::class.java)
                } catch (e: Exception) {
                    null
                }
            }
        }

    suspend fun saveAuthToken(token: String) {
        cachedAuthToken = token
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }

    fun getAuthTokenSync(): String {
        return runBlocking {
            getAuthToken()
        }
    }

    suspend fun getAuthToken(): String {
        return context.dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.AUTH_TOKEN] ?: ""
            }
            .first()
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_DATA] = gson.toJson(user)
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.AUTH_TOKEN)
            preferences.remove(PreferencesKeys.USER_DATA)
        }
    }
}