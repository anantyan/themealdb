package id.anantyan.foodapps.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_meals")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id_meal")
    val idMeal: Int? = null,

    @ColumnInfo("str_meal_thumb")
    val strMealThumb: String? = null,

    @ColumnInfo("str_meal")
    val strMeal: String? = null,

    @ColumnInfo("token_user")
    val tokenUser: String? = null
)
