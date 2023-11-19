package id.anantyan.foodapps.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.domain.model.RecipeModel
import id.anantyan.foodapps.domain.repository.MainUseCase
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    fun results(): LiveData<List<RecipeModel>> {
        return mainUseCase.executeRecipeResults().asLiveData()
    }
}