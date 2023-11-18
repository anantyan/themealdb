package id.anantyan.foodapps.data.local.datasource

import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    suspend fun setLogin(value: Boolean)
    fun getLogin(): Flow<Boolean>
    suspend fun setToken(value: String)
    fun getToken(): Flow<String>
}