package id.anantyan.foodapps.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.anantyan.foodapps.data.local.dao.RecipesDao
import id.anantyan.foodapps.data.local.entities.RecipeEntity

@Database(
    entities = [
        RecipeEntity::class
    ], version = 1, exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}