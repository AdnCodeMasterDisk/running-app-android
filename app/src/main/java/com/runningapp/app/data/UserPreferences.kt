package com.runningapp.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")
        private val KEY_AUTH = stringPreferencesKey(name = "key_auth")
        private val KEY_USERID = intPreferencesKey(name = "key_userid")
        private val KEY_MONTHLY_GOAL = intPreferencesKey(name = "key_monthly_goal")
    }

    val authToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    val userId: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[KEY_USERID]
        }

    val monthlyGoal: Flow<Int?>
        get() = context.dataStore.data.map { preferences ->
            preferences[KEY_MONTHLY_GOAL]
        }

    suspend fun saveAuthToken(authToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USERID] = userId
        }
    }

    suspend fun saveMonthlyGoal(monthlyGoal: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_MONTHLY_GOAL] = monthlyGoal
        }
    }

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}