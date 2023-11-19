package id.anantyan.foodapps.presentation.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.domain.repository.MainUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    private var _bookmarked: MutableLiveData<Boolean> = MutableLiveData()

    val bookmarked: LiveData<Boolean> = _bookmarked

    fun recipeBookmark(idMeal: Int) {
        viewModelScope.launch {
            val response = mainUseCase.executeResult(idMeal)
            if (response != null) {
                mainUseCase.executeRecipeBookmark(response)
                _bookmarked.postValue(true)
            } else {
                _bookmarked.postValue(false)
            }
        }
    }

    fun recipeUnbookmark(idMeal: Int) {
        viewModelScope.launch {
            mainUseCase.executeRecipeUnbookmark(idMeal)
            _bookmarked.postValue(false)
        }
    }

    fun recipeCheckResult(idMeal: Int) {
        viewModelScope.launch {
            val response = mainUseCase.executeRecipeCheckMeal(idMeal)
            if (response != null) {
                _bookmarked.postValue(true)
            } else {
                _bookmarked.postValue(false)
            }
        }
    }

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