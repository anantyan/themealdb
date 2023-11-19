package id.anantyan.foodapps.domain.model

import id.anantyan.foodapps.data.local.entities.RecipeEntity
import id.anantyan.foodapps.data.remote.model.MealsItem

data class RecipeModel(
    val id: Int? = null,
    val thumbnail: String? = null,
    val title: String? = null
)

fun RecipeEntity.toModel(): RecipeModel {
    return RecipeModel(idMeal, strMealThumb, strMeal)
}

fun MealsItem.toEntity(tokenUser: String): RecipeEntity {
    val id = if (!idMeal.isNullOrEmpty()) {
        idMeal.toString().toInt()
    } else {
        -1
    }
    return RecipeEntity(idMeal = id, strMealThumb, strMeal, tokenUser)
}
