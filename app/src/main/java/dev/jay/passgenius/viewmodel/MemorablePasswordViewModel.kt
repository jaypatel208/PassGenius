package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jay.passgenius.usecase.GetWordsForPasswordUseCase
import dev.jay.passgenius.utils.PasswordUtility
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemorablePasswordViewModel @Inject constructor(private val getWordsForPasswordUseCase: GetWordsForPasswordUseCase) :
    ViewModel() {
    private val _digitsValue = mutableStateOf(1)
    private val _wordsValue = mutableStateOf(3)
    private val _wordList = mutableStateOf<List<String>>(emptyList())
    private val _generatedPassword = mutableStateOf("")

    val generatedPassword: State<String> = _generatedPassword
    val digitsValue: State<Int> = _digitsValue
    val wordsValue: State<Int> = _wordsValue

    init {
        initGeneratePassword()
    }

    fun initGeneratePassword() {
        viewModelScope.launch {
            _wordList.value = getWordsForPasswordUseCase.getWords()
            if (_wordList.value.isNotEmpty()) {
                generateMemorablePassword()
            }
        }
    }

    private fun generateMemorablePassword() {
        viewModelScope.launch {
            _generatedPassword.value = PasswordUtility.generateMemorablePassword(_wordList.value, 5, 1)
        }
    }
}