package id.anantyan.foodapps.domain.repository

import androidx.paging.PagingData
import id.anantyan.foodapps.data.local.entities.RecipeEntity
import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.data.remote.model.RecipesResponse
import id.anantyan.foodapps.domain.model.RecipeModel
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

    suspend fun recipeBookmark(item: MealsItem)
    suspend fun recipeUnbookmark(idMeal: Int)
    suspend fun recipeCheckMeal(idMeal: Int): RecipeModel?
    fun recipeResults(): Flow<List<RecipeModel>>
}