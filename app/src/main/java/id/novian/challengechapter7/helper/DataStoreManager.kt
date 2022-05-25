package id.novian.challengechapter7.helper

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    suspend fun saveEmail(email: String) {
        context.dataStore.edit {
            it[EMAIL_KEY] = email
        }
    }

    fun getEmail(): Flow<String> {
        return context.dataStore.data.map {
            it[EMAIL_KEY] ?: ""
        }
    }

    suspend fun saveStatusLogin() {
        context.dataStore.edit {
            it[LOGIN_KEY] = true
        }
    }

    fun getStatusLogin(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[LOGIN_KEY] ?: false
        }
    }

    suspend fun saveStatusLogout() {
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "datastore"
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val LOGIN_KEY = booleanPreferencesKey("login")
        private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    }
}