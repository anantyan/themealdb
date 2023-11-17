package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.data.remote.model.MealsItem
import kotlinx.coroutines.flow.Flow

class MainUseCase(private val mainRepository: MainRepository) {
    suspend fun executeResults(query: String?): List<MealsItem> = mainRepository.results(query)
    suspend fun executeResult(id: Int): MealsItem? = mainRepository.result(id)
    suspend fun executeCategories(): List<MealsItem> = mainRepository.categories()
    suspend fun executeAreas(): List<MealsItem> = mainRepository.areas()

    suspend fun executeSetLogin(value: Boolean) = mainRepository.setLogin(value)
    fun executeGetLogin(): Flow<Boolean> = mainRepository.getLogin()
    suspend fun executeSetUsrId(value: Int) = mainRepository.setUsrId(value)
    fun executeGetUsrId(): Flow<Int> = mainRepository.getUsrId()
}