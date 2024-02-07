package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.usecase.SavePasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavePasswordScreenViewModel @Inject constructor(private val savePasswordUseCase: SavePasswordUseCase) :
    ViewModel() {
    private val _showSnackBar = mutableStateOf(false)
    val showSnackBar: State<Boolean> = _showSnackBar
    fun savePassword(passwordModel: PasswordModel) {
        viewModelScope.launch {
            savePasswordUseCase.savePassword(passwordModel)
        }
    }

    fun updateShowSnackBar(newShowSnackBar: Boolean) {
        _showSnackBar.value = newShowSnackBar
    }
}