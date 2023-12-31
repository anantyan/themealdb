package id.anantyan.foodapps.data.remote.model

import com.google.gson.annotations.SerializedName

data class AuthsResponse(

	@field:SerializedName("token")
	val token: String? = null
)

data class UsersResponse(
	@field:SerializedName("data")
	val results: List<DataItem>? = emptyList(),
)

data class UserResponse(
	@field:SerializedName("data")
	val result: DataItem? = null
)

data class DataItem(
	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
