package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.data.remote.model.MealsItem
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun results(query: String?): List<MealsItem>
    suspend fun result(id: Int): MealsItem?
    suspend fun categories(): List<MealsItem>
    suspend fun areas(): List<MealsItem>

    suspend fun setLogin(value: Boolean)
    fun getLogin(): Flow<Boolean>
    suspend fun setUsrId(value: Int)
    fun getUsrId(): Flow<Int>
}