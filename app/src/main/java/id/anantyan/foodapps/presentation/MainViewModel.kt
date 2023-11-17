package id.anantyan.foodapps.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.foodapps.domain.repository.MainUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    val getLogin: Boolean = runBlocking { mainUseCase.executeGetLogin().first() }
}