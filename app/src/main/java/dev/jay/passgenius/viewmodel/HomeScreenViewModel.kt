package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jay.passgenius.di.models.PasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStrengthResult
import dev.jay.passgenius.usecase.DeletePasswordUseCase
import dev.jay.passgenius.usecase.FindPasswordsUseCase
import dev.jay.passgenius.usecase.GetAllPasswordsUseCase
import dev.jay.passgenius.usecase.PasswordStrengthCheckUseCase
import dev.jay.passgenius.usecase.UpdatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val findPasswordsUseCase: FindPasswordsUseCase,
    private val passwordStrengthCheckUseCase: PasswordStrengthCheckUseCase,
    private val getAllPasswordsUseCase: GetAllPasswordsUseCase
) : ViewModel() {
    private val _allStoredPasswords = mutableStateOf<List<PasswordStoreModel>>(emptyList())
    private val _totalPasswordsSize = mutableStateOf(0)
    private val _strongPasswords = mutableStateOf<List<PasswordStoreModel>>(emptyList())
    private val _mediocrePasswords = mutableStateOf<List<PasswordStoreModel>>(emptyList())
    private val _passwordStrengthResult = mutableStateOf<PasswordStrengthResult?>(null)

    val totalPasswordsSize: State<Int> = _totalPasswordsSize
    val strongPasswords: State<List<PasswordStoreModel>> = _strongPasswords
    val mediocrePasswords: State<List<PasswordStoreModel>> = _mediocrePasswords
    val allStoredPasswords: State<List<PasswordStoreModel>> = _allStoredPasswords
    private val passwordStrengthResult: State<PasswordStrengthResult?> = _passwordStrengthResult

    init {
        getAllStoredPasswords()
    }

    fun getAllStoredPasswords() {
        _allStoredPasswords.value = getAllPasswordsUseCase.getAllPassword()
        _totalPasswordsSize.value = allStoredPasswords.value.size

        if (totalPasswordsSize.value > 0) {
            _passwordStrengthResult.value = passwordStrengthCheckUseCase.checkPasswordStrength(allStoredPasswords.value)
            _strongPasswords.value = passwordStrengthResult.value?.strongPasswords ?: emptyList()
            _mediocrePasswords.value = passwordStrengthResult.value?.mediocrePasswords ?: emptyList()
        }
    }
}