package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.usecase.UpdatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class EditPasswordScreenViewModel @Inject constructor(private val updatePasswordUseCase: UpdatePasswordUseCase) :
    ViewModel() {
    private val _showSnackBar = mutableStateOf(false)
    val showSnackBar: State<Boolean> = _showSnackBar

    fun updateShowSnackBar(newShowSnackBar: Boolean) {
        _showSnackBar.value = newShowSnackBar
    }

    fun updatePassword(passwordModel: PasswordModel){
        updatePasswordUseCase.updatePassword(passwordModel)
    }
}