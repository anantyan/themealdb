package id.anantyan.foodapps.domain.model

data class IngredientsItem(
    val strIngredients: List<String>? = emptyList()
)

data class MeasuresItem(
    val strMeasures: List<String>? = emptyList()
)
