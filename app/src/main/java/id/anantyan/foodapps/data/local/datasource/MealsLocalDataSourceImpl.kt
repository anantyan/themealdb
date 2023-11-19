package id.anantyan.foodapps.data.local.datasource

import id.anantyan.foodapps.data.local.dao.RecipesDao
import id.anantyan.foodapps.data.local.entities.RecipeEntity
import id.anantyan.foodapps.domain.model.RecipeModel
import id.anantyan.foodapps.domain.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MealsLocalDataSourceImpl(
    private val recipesDao: RecipesDao
) : MealsLocalDataSource {
    override suspend fun bookmark(item: RecipeEntity) {
        recipesDao.bookmark(item)
    }

    override suspend fun unbookmark(idMeal: Int, tokenUser: String) {
        recipesDao.unbookmark(idMeal, tokenUser)
    }

    override suspend fun checkMeal(idMeal: Int, tokenUser: String): RecipeModel? {
        return if (recipesDao.checkMeal(idMeal, tokenUser) != null) {
            recipesDao.checkMeal(idMeal, tokenUser)?.toModel()
        } else {
            null
        }
    }

    override fun results(tokenUser: String): Flow<List<RecipeModel>> {
        return recipesDao.selectFoods(tokenUser).map {  list: List<RecipeEntity> ->
            list.map { it.toModel() }
        }
    }

}