package id.anantyan.foodapps.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.data.remote.model.MealsItem
import id.anantyan.foodapps.domain.repository.MainRepository
import id.anantyan.foodapps.domain.repository.MainUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    private var _results: MutableLiveData<UIState<List<MealsItem>>> = MutableLiveData()

    val results: LiveData<UIState<List<MealsItem>>> = _results

    fun results(query: String?) {
        viewModelScope.launch {
            _results.postValue(UIState.Loading())
            val response = mainUseCase.executeResults(query)
            if (response.isNotEmpty()) {
                _results.postValue(UIState.Success(response))
            } else {
                _results.postValue(UIState.Error(null, R.string.txt_invalid_recipe_meals))
            }
        }
    }
}