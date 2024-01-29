package dev.jay.passgenius.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RobustPasswordViewModel @Inject() constructor() : ViewModel() {

    private val _digitsValue = mutableStateOf(3)
    private val _capitalValue = mutableStateOf(3)
    private val _symbolsValue = mutableStateOf(2)
    private val _lengthValue = mutableStateOf(12f)

    val capitalValue: State<Int> get() = _capitalValue
    val digitsValue: State<Int> get() = _digitsValue
    val symbolsValue: State<Int> get() = _symbolsValue
    val lengthValue: State<Float> get() = _lengthValue

    fun updateLengthValue(newLengthValue: Float) {
        _lengthValue.value = newLengthValue
    }

    fun updateDigitsValue(newDigitValue: Int) {
        _digitsValue.value = newDigitValue
    }

    fun updateCapitalValue(newCapitalsValue: Int) {
        _capitalValue.value = newCapitalsValue
    }

    fun updateSymbolsValue(newSymbolsValue: Int) {
        _symbolsValue.value = newSymbolsValue
    }

    fun getMaxCharacteristicValue(): Int {
        val totalCharacters = (_lengthValue.value - 1).toInt()
        val currentSum = _capitalValue.value + _digitsValue.value + _symbolsValue.value

        return when {
            currentSum >= totalCharacters -> {
                0
            }

            else -> {
                totalCharacters - currentSum
            }
        }
    }

    fun generatePassword(): String {
        val length = lengthValue.value.toInt()
        val capitalChars = capitalValue.value
        val digits = digitsValue.value
        val symbols = symbolsValue.value
        val smallChars = length - (capitalChars + digits + symbols)


        val capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val smallLetters = "abcdefghijklmnopqrstuvwxyz"
        val numericDigits = "0123456789"
        val specialSymbols = "~`!@#$%^&*()_-+={[}]|:;\"'<,>.?"

        val allCharacters = capitalLetters + smallLetters + numericDigits + specialSymbols

        if (length < capitalChars + smallChars + digits + symbols) {
            throw IllegalArgumentException("Total characters cannot be greater than the password length.")
        }

        val passwordBuilder = StringBuilder()

        // Add required number of characters for each category
        repeat(capitalChars) { passwordBuilder.append(capitalLetters[Random.nextInt(capitalLetters.length)]) }
        repeat(smallChars) { passwordBuilder.append(smallLetters[Random.nextInt(smallLetters.length)]) }
        repeat(digits) { passwordBuilder.append(numericDigits[Random.nextInt(numericDigits.length)]) }
        repeat(symbols) { passwordBuilder.append(specialSymbols[Random.nextInt(specialSymbols.length)]) }

        // Add remaining characters to meet the specified length
        repeat(length - (capitalChars + smallChars + digits + symbols)) {
            passwordBuilder.append(allCharacters[Random.nextInt(allCharacters.length)])
        }

        // Shuffle the characters in the password
        val shuffledPassword = passwordBuilder.toString().toCharArray().also { it.shuffle() }
        return String(shuffledPassword)
    }
}