package id.anantyan.foodapps.presentation.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.domain.repository.MainUseCase
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    fun recipeResult(id: Int): LiveData<UIState<MealsItem>> {
        return liveData {
            emit(UIState.Loading())
            val response = mainUseCase.executeResult(id)
            if (response != null) {
                emit(UIState.Success(response))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_recipe_meals))
            }
        }
    }
}