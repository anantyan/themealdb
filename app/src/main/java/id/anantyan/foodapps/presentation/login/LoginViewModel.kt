package id.anantyan.foodapps.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.AuthsResponse
import id.anantyan.foodapps.domain.repository.MainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    private var _login: MutableLiveData<UIState<AuthsResponse>> = MutableLiveData()

    val login: LiveData<UIState<AuthsResponse>> = _login

    fun login(
        email: String,
        password: String
    ) {
        _login.postValue(UIState.Loading())
        viewModelScope.launch {
            val response = mainUseCase.executeLogin(email, password)
            if (response != null) {
                _login.postValue(UIState.Success(response))
            } else {
                _login.postValue(UIState.Error(null, R.string.txt_invalid_login))
            }
        }
    }
}