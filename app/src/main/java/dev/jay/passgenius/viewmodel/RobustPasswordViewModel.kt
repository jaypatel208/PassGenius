package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jay.passgenius.usecase.RobustPasswordUseCase
import javax.inject.Inject

@HiltViewModel
class RobustPasswordViewModel @Inject constructor(private val robustPasswordUseCase: RobustPasswordUseCase) :
    ViewModel() {

    private val _digitsValue = mutableStateOf(3)
    private val _capitalValue = mutableStateOf(3)
    private val _symbolsValue = mutableStateOf(2)
    private val _lengthValue = mutableStateOf(12f)

    val digitsValue: State<Int> = _digitsValue
    val capitalValue: State<Int> = _capitalValue
    val symbolsValue: State<Int> = _symbolsValue
    val lengthValue: State<Float> = _lengthValue

    val maxCharacteristicValue: State<Int> = derivedStateOf {
        (
                robustPasswordUseCase.getMaxCharacteristicValue(
                    lengthValue.value.toInt(),
                    capitalValue.value,
                    digitsValue.value,
                    symbolsValue.value
                )
                )
    }

    val generatedPassword: State<String> = derivedStateOf {
        (
                robustPasswordUseCase.generatePassword(
                    lengthValue.value.toInt(),
                    capitalValue.value,
                    digitsValue.value,
                    symbolsValue.value
                )
                )
    }

    fun regeneratePassword() {
        _digitsValue.value++
        _digitsValue.value--
    }

    fun updateDigitsValue(newDigitValue: Int) {
        _digitsValue.value = newDigitValue
    }

    fun updateLengthValue(newLengthValue: Float) {
        _lengthValue.value = newLengthValue
    }

    fun updateCapitalValue(newCapitalsValue: Int) {
        _capitalValue.value = newCapitalsValue
    }

    fun updateSymbolsValue(newSymbolsValue: Int) {
        _symbolsValue.value = newSymbolsValue
    }
}