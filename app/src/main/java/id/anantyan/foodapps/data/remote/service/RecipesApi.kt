package id.anantyan.foodapps.data.remote.service

import id.anantyan.foodapps.data.remote.model.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {
    @GET("search.php")
    suspend fun results(
        @Query("s") query: String? = null
    ): Response<RecipesResponse>

    @GET("lookup.php")
    suspend fun result(
        @Query("i") id: Int? = 0
    ): Response<RecipesResponse>

    @GET("list.php")
    suspend fun categories(
        @Query("c") type: String? = "list"
    ): Response<RecipesResponse>

    @GET("list.php")
    suspend fun areas(
        @Query("c") type: String? = "list"
    ): Response<RecipesResponse>
}