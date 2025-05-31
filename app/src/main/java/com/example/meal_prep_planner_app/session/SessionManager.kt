package com.example.meal_prep_planner_app.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val USER_ID_KEY = intPreferencesKey("user_id")
    }

    suspend fun saveUserId(userId: Int) {
        dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }

    val userIdFlow: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }
}
