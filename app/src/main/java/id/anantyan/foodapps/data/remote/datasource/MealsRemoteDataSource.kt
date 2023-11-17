package id.anantyan.foodapps.data.remote.datasource

import id.anantyan.foodapps.data.remote.model.MealsItem

interface MealsRemoteDataSource {
    suspend fun results(query: String?): List<MealsItem>
    suspend fun result(id: Int): MealsItem?
    suspend fun categories(): List<MealsItem>
    suspend fun areas(): List<MealsItem>
}