package id.anantyan.foodapps.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.anantyan.foodapps.data.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmark(item: RecipeEntity): Long

    @Query("DELETE FROM tbl_meals WHERE id_meal=:idMeal AND token_user=:tokenUser")
    suspend fun unbookmark(idMeal: Int, tokenUser: String)

    @Query("SELECT * FROM tbl_meals WHERE id_meal=:idMeal AND token_user=:tokenUser")
    suspend fun checkMeal(idMeal: Int, tokenUser: String): RecipeEntity?

    @Query("SELECT * FROM tbl_meals WHERE token_user=:tokenUser")
    fun selectFoods(tokenUser: String): Flow<List<RecipeEntity>>
}