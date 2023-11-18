package id.anantyan.foodapps.domain.repository

import androidx.paging.PagingData
import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import kotlinx.coroutines.flow.Flow

class MainUseCase(private val mainRepository: MainRepository) {
    suspend fun executeResults(query: String?): List<MealsItem> = mainRepository.results(query)
    suspend fun executeResultsByCategory(category: String): List<MealsItem> = mainRepository.resultsByCategory(category)
    suspend fun executeResultsByArea(area: String): List<MealsItem> = mainRepository.resultsByArea(area)
    suspend fun executeResult(id: Int): MealsItem? = mainRepository.result(id)
    suspend fun executeCategories(): List<MealsItem> = mainRepository.categories()
    suspend fun executeAreas(): List<MealsItem> = mainRepository.areas()

    fun executeCheckLogin(): Flow<Boolean> = mainRepository.checkLogin()
    suspend fun executeLogout() = mainRepository.logout()

    suspend fun executeLogin(email: String, password: String): AuthsResponse? = mainRepository.login(email, password)
    fun executeUserResults(): Flow<PagingData<DataItem>> = mainRepository.userResults()
    suspend fun executeUserResult(id: Int): DataItem? = mainRepository.userResult(id)
}