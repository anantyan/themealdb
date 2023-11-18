package id.anantyan.foodapps.data.repository

import android.util.Log
import androidx.paging.PagingData
import id.anantyan.foodapps.data.local.datasource.PreferencesDataSource
import id.anantyan.foodapps.data.remote.datasource.AuthsRemoteDataSource
import id.anantyan.foodapps.data.remote.datasource.MealsRemoteDataSource
import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.data.remote.model.UsersResponse
import id.anantyan.foodapps.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainRepositoryImpl(
    private val mealsRemoteDataSource: MealsRemoteDataSource,
    private val authsRemoteDataSource: AuthsRemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource
) : MainRepository {
    override suspend fun results(query: String?): List<MealsItem> {
        return try {
            mealsRemoteDataSource.results(query)
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override suspend fun resultsByCategory(category: String): List<MealsItem> {
        return try {
            mealsRemoteDataSource.resultsByCategory(category)
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override suspend fun resultsByArea(area: String): List<MealsItem> {
        return try {
            mealsRemoteDataSource.resultsByArea(area)
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override suspend fun result(id: Int): MealsItem? {
        return try {
            mealsRemoteDataSource.result(id)
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            null
        }
    }

    override suspend fun categories(): List<MealsItem> {
        return try {
            mealsRemoteDataSource.categories()
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override suspend fun areas(): List<MealsItem> {
        return try {
            mealsRemoteDataSource.areas()
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            emptyList()
        }
    }

    override fun checkLogin(): Flow<Boolean> {
        return preferencesDataSource.getLogin().combine(preferencesDataSource.getToken()) { login: Boolean, token: String ->
            login && token.isNotEmpty()
        }
    }

    override suspend fun logout() {
        preferencesDataSource.setLogin(false)
        preferencesDataSource.setToken("")
    }

    override suspend fun login(email: String, password: String): AuthsResponse? {
        return try {
            val response = authsRemoteDataSource.login(email, password)
            if (response != null) {
                preferencesDataSource.setLogin(true)
                preferencesDataSource.setToken(response.token ?: "")
                response
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            null
        }
    }

    override fun userResults(): Flow<PagingData<DataItem>> {
        return authsRemoteDataSource.results()
    }

    override suspend fun userResult(id: Int): DataItem? {
        return try {
            authsRemoteDataSource.result(id)
        } catch (e: Exception) {
            Log.d("MEALS-DEBUG", e.message.toString())
            null
        }
    }
}