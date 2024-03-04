package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.di.models.PasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStrengthResult
import dev.jay.passgenius.usecase.DeletePasswordUseCase
import dev.jay.passgenius.usecase.FindPasswordsUseCase
import dev.jay.passgenius.usecase.GetAllPasswordsUseCase
import dev.jay.passgenius.usecase.PasswordStrengthCheckUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val findPasswordsUseCase: FindPasswordsUseCase,
    private val passwordStrengthCheckUseCase: PasswordStrengthCheckUseCase,
    private val getAllPasswordsUseCase: GetAllPasswordsUseCase
) : ViewModel() {
    private val _allStoredPasswords = mutableStateListOf<PasswordStoreModel>()
    private val _totalPasswordsSize = mutableStateOf(0)
    private val _strongPasswords = mutableStateListOf<PasswordStoreModel>()
    private val _mediocrePasswords = mutableStateListOf<PasswordStoreModel>()
    private val _passwordStrengthResult = mutableStateOf<PasswordStrengthResult?>(null)
    private val _clickedPassword = mutableStateOf<PasswordStoreModel?>(null)

    val totalPasswordsSize: State<Int> = _totalPasswordsSize
    val strongPasswords: SnapshotStateList<PasswordStoreModel> = _strongPasswords
    val mediocrePasswords: SnapshotStateList<PasswordStoreModel> = _mediocrePasswords
    val allStoredPasswords: SnapshotStateList<PasswordStoreModel> = _allStoredPasswords
    val clickedPassword: State<PasswordStoreModel?> = _clickedPassword
    private val passwordStrengthResult: State<PasswordStrengthResult?> = _passwordStrengthResult

    init {
        getAllStoredPasswords()
    }

    fun updateClickedPassword(passwordStoreModel: PasswordStoreModel) {
        _clickedPassword.value = passwordStoreModel
    }

    fun deletePassword(passwordStoreModel: PasswordStoreModel) {
        val passwordToBeDeleted = PasswordModel(
            id = passwordStoreModel.id,
            site = passwordStoreModel.site,
            username = passwordStoreModel.username,
            password = passwordStoreModel.password
        )
        deletePasswordUseCase.deletePassword(passwordToBeDeleted)
    }

    fun getAllStoredPasswords() {
        viewModelScope.launch {
            val allPasswords = getAllPasswordsUseCase.getAllPassword()
            _allStoredPasswords.apply {
                clear()
                addAll(allPasswords)
            }

            _totalPasswordsSize.value = allPasswords.size

            if (_totalPasswordsSize.value > 0) {
                val passwordStrengthResult = passwordStrengthCheckUseCase.checkPasswordStrength(allPasswords)
                _passwordStrengthResult.value = passwordStrengthResult

                _strongPasswords.apply {
                    clear()
                    addAll(passwordStrengthResult.strongPasswords)
                }

                _mediocrePasswords.apply {
                    clear()
                    addAll(passwordStrengthResult.mediocrePasswords)
                }
            }
        }
    }

    fun onStrongPasswordClick() {
        _allStoredPasswords.apply {
            clear()
            addAll(_strongPasswords)
        }
    }

    fun onMediocrePasswordClick() {
        _allStoredPasswords.apply {
            clear()
            addAll(_mediocrePasswords)
        }
    }
}