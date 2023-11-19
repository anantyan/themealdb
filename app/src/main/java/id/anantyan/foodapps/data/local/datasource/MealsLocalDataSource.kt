package id.anantyan.foodapps.data.local.datasource

import id.anantyan.foodapps.data.local.entities.RecipeEntity
import id.anantyan.foodapps.domain.model.RecipeModel
import kotlinx.coroutines.flow.Flow

interface MealsLocalDataSource {
    suspend fun bookmark(item: RecipeEntity)
    suspend fun unbookmark(idMeal: Int, tokenUser: String)
    suspend fun checkMeal(idMeal: Int, tokenUser: String): RecipeModel?
    fun results(tokenUser: String): Flow<List<RecipeModel>>
}