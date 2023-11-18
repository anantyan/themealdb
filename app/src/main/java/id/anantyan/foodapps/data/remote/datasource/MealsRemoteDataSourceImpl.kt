package id.anantyan.foodapps.data.remote.datasource

import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.data.remote.service.RecipesApi

class MealsRemoteDataSourceImpl(
    private val recipesApi: RecipesApi
) : MealsRemoteDataSource {
    override suspend fun results(query: String?): List<MealsItem> {
        val response = recipesApi.results(query)
        return if (response.isSuccessful) {
            response.body()?.meals ?: emptyList()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }

    override suspend fun resultsByCategory(category: String): List<MealsItem> {
        val response = recipesApi.resultsByCategory(category)
        return if (response.isSuccessful) {
            response.body()?.meals ?: emptyList()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }

    override suspend fun resultsByArea(area: String): List<MealsItem> {
        val response = recipesApi.resultsByArea(area)
        return if (response.isSuccessful) {
            response.body()?.meals ?: emptyList()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }

    override suspend fun result(id: Int): MealsItem? {
        val response = recipesApi.result(id)
        return if (response.isSuccessful) {
            if (!response.body()?.meals.isNullOrEmpty()) {
                response.body()?.meals?.get(0)
            } else {
                null
            }
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }

    override suspend fun categories(): List<MealsItem> {
        val response = recipesApi.categories()
        return if (response.isSuccessful) {
            response.body()?.meals ?: emptyList()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }

    override suspend fun areas(): List<MealsItem> {
        val response = recipesApi.areas()
        return if (response.isSuccessful) {
            response.body()?.meals ?: emptyList()
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }
}