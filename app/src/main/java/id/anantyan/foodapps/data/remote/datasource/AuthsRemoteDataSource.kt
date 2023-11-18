package id.anantyan.foodapps.data.remote.datasource

import androidx.paging.PagingData
import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.UsersResponse
import kotlinx.coroutines.flow.Flow

interface AuthsRemoteDataSource {
    suspend fun login(email: String, password: String): AuthsResponse?
    fun results(): Flow<PagingData<DataItem>>
    suspend fun result(id: Int): DataItem?
}