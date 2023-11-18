package id.anantyan.foodapps.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.DataItem
import id.anantyan.foodapps.domain.repository.MainUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    fun userResult(id: Int): LiveData<UIState<DataItem>> {
        return liveData {
            emit(UIState.Loading())
            val response = mainUseCase.executeUserResult(id)
            if (response != null) {
                emit(UIState.Success(response))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_profile))
            }
        }
    }
}