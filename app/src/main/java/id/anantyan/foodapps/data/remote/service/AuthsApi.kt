package id.anantyan.foodapps.data.remote.service

import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.UserResponse
import id.anantyan.foodapps.data.remote.model.UsersResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthsApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthsResponse>

    @GET("users")
    suspend fun results(
        @Query("page") page: Int? = 1
    ): Response<UsersResponse>

    @GET("users/{id}")
    suspend fun result(
        @Path("id") id: Int? = 0
    ): Response<UserResponse>
}