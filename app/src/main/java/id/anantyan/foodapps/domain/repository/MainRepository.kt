package id.anantyan.foodapps.domain.repository

import androidx.paging.PagingData
import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun results(query: String?): List<MealsItem>
    suspend fun resultsByCategory(category: String): List<MealsItem>
    suspend fun resultsByArea(area: String): List<MealsItem>
    suspend fun result(id: Int): MealsItem?
    suspend fun categories(): List<MealsItem>
    suspend fun areas(): List<MealsItem>

    fun checkLogin(): Flow<Boolean>
    suspend fun logout()

    suspend fun login(email: String, password: String): AuthsResponse?
    fun userResults(): Flow<PagingData<DataItem>>
    suspend fun userResult(id: Int): DataItem?
}