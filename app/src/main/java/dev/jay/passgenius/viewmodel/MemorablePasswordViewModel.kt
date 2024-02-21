package dev.jay.passgenius.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.jay.passgenius.usecase.GetWordsForPasswordUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MemorablePasswordViewModel @Inject constructor(
    private val getWordsForPasswordUseCase: GetWordsForPasswordUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _digitsValue = mutableStateOf(1)
    private val _wordsValue = mutableStateOf(3)
    private val _generatedPassword = mutableStateOf("")

    val generatedPassword: State<String> = _generatedPassword

    init {
        reGeneratePassword()
    }

    fun reGeneratePassword() {
        generatePassword()
    }

    private fun generatePassword() {
        viewModelScope.launch {
            _generatedPassword.value = getWordsForPasswordUseCase.generateMemorablePassword(
                context = context,
                wordsAmount = _wordsValue.value,
                digitsAmount = _digitsValue.value
            )
        }
    }

    fun updateDigitsValue(newDigitsValue: Int) {
        _digitsValue.value = newDigitsValue
        reGeneratePassword()
    }

    fun updateWordsValue(newWordsValue: Int) {
        _wordsValue.value = newWordsValue
        reGeneratePassword()
    }
}