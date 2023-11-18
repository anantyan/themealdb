package id.anantyan.foodapps.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.UsersResponse
import id.anantyan.foodapps.data.remote.service.AuthsApi
import kotlinx.coroutines.flow.Flow

class AuthsRemoteDataSourceImpl(
    private val authsApi: AuthsApi
) : AuthsRemoteDataSource {
    override suspend fun login(email: String, password: String): AuthsResponse? {
        val response = authsApi.login(email, password)
        return if (response.isSuccessful) {
            response.body()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }

    override fun results(): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            pagingSourceFactory = { UsersPagingSource(authsApi) }
        ).flow
    }

    override suspend fun result(id: Int): DataItem? {
        val response = authsApi.result(id)
        return if (response.isSuccessful) {
            response.body()?.result
        } else {
            throw Exception()
        }
    }
}