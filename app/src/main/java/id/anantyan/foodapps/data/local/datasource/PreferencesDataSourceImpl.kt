package id.anantyan.foodapps.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.anantyan.foodapps.common.getValue
import id.anantyan.foodapps.common.setValue
import kotlinx.coroutines.flow.Flow

class PreferencesDataSourceImpl(
    private val context: Context
) : PreferencesDataSource {

    companion object {
        private const val DATASTORE_SETTINGS: String = "SETTINGS"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_SETTINGS)
        private val LOGIN = booleanPreferencesKey("LOGIN")
        private val TOKEN = stringPreferencesKey("TOKEN")
    }

    override suspend fun setLogin(value: Boolean) {
        context.dataStore.setValue(LOGIN, value)
    }

    override fun getLogin(): Flow<Boolean> {
        return context.dataStore.getValue(LOGIN, false)
    }

    override suspend fun setToken(value: String) {
        context.dataStore.setValue(TOKEN, value)
    }

    override fun getToken(): Flow<String> {
        return context.dataStore.getValue(TOKEN, "")
    }
}