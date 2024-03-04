package dev.jay.passgenius.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.di.models.PasswordStoreModel
import dev.jay.passgenius.usecase.DeletePasswordUseCase
import dev.jay.passgenius.usecase.GetAllPasswordsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPasswordScreenViewModel @Inject constructor(
    private val getAllPasswordsUseCase: GetAllPasswordsUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase
) :
    ViewModel() {
    private val _allStoredPassword = MutableStateFlow(listOf<PasswordStoreModel>())
    private val _searchText = MutableStateFlow("")

    val searchText = _searchText.asStateFlow()
    val allStoredPassword = searchText.combine(_allStoredPassword) { text, passwords ->
        if (text.isBlank()) {
            passwords
        } else {
            passwords.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _allStoredPassword.value)

    init {
        getAllStoredPasswords()
    }

    fun getAllStoredPasswords() {
        viewModelScope.launch {
            _allStoredPassword.update { getAllPasswordsUseCase.getAllPassword().toMutableList() }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
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
}