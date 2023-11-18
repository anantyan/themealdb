package id.anantyan.foodapps.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.domain.repository.MainUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    private var _mealsResults: MutableLiveData<UIState<List<MealsItem>>> = MutableLiveData()

    val mealsResults: LiveData<UIState<List<MealsItem>>> = _mealsResults

    fun mealsResults() {
        viewModelScope.launch {
            _mealsResults.postValue(UIState.Loading())
            val response = mainUseCase.executeResults("")
            if (response.isNotEmpty()) {
                _mealsResults.postValue(UIState.Success(response))
            } else {
                _mealsResults.postValue(UIState.Error(null, R.string.txt_invalid_recipe_meals))
            }
        }
    }

    fun mealsResultsByCategory(category: String) {
        viewModelScope.launch {
            _mealsResults.postValue(UIState.Loading())
            val response = mainUseCase.executeResultsByCategory(category)
            if (response.isNotEmpty()) {
                _mealsResults.postValue(UIState.Success(response))
            } else {
                _mealsResults.postValue(UIState.Error(null, R.string.txt_invalid_recipe_meals))
            }
        }
    }

    fun mealsResultsByArea(area: String) {
        viewModelScope.launch {
            _mealsResults.postValue(UIState.Loading())
            val response = mainUseCase.executeResultsByArea(area)
            if (response.isNotEmpty()) {
                _mealsResults.postValue(UIState.Success(response))
            } else {
                _mealsResults.postValue(UIState.Error(null, R.string.txt_invalid_recipe_meals))
            }
        }
    }

    fun categoryResults(): LiveData<List<MealsItem>> {
        return liveData {
            emit(mainUseCase.executeCategories())
        }
    }

    fun userResults(): LiveData<PagingData<DataItem>> {
        return mainUseCase.executeUserResults().cachedIn(viewModelScope).asLiveData()
    }
}