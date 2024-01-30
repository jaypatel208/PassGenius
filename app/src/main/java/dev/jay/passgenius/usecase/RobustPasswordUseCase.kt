package dev.jay.passgenius.usecase

import javax.inject.Inject
import kotlin.random.Random

class RobustPasswordUseCase @Inject constructor() {
    fun getMaxCharacteristicValue(lengthValue: Int, capitalValue: Int, digitsValue: Int, symbolsValue: Int): Int {
        val totalCharacters = (lengthValue - 1)
        val currentSum = capitalValue + digitsValue + symbolsValue

        return when {
            currentSum >= totalCharacters -> {
                0
            }

            else -> {
                totalCharacters - currentSum
            }
        }
    }

    fun generatePassword(lengthValue: Int, capitalValue: Int, digitsValue: Int, symbolsValue: Int): String {
        val smallChars = lengthValue - (capitalValue + digitsValue + symbolsValue)

        val capitalLetters = "MABCDYZGHIJKLQRSEFNOPWXTUV"
        val smallLetters = "jkstabhiuvwxycdefglmnopqrz"
        val numericDigits = "8926743015"
        val specialSymbols = "~`!@#$^&*()%_-+={[}]|:;\"',>.?<"

        val allCharacters = capitalLetters + smallLetters + numericDigits + specialSymbols

        if (lengthValue < capitalValue + smallChars + digitsValue + symbolsValue) {
            throw IllegalArgumentException("Total characters cannot be greater than the password length.")
        }

        val passwordBuilder = StringBuilder()

        repeat(capitalValue) { passwordBuilder.append(capitalLetters[Random.nextInt(capitalLetters.length)]) }
        repeat(smallChars) { passwordBuilder.append(smallLetters[Random.nextInt(smallLetters.length)]) }
        repeat(digitsValue) { passwordBuilder.append(numericDigits[Random.nextInt(numericDigits.length)]) }
        repeat(symbolsValue) { passwordBuilder.append(specialSymbols[Random.nextInt(specialSymbols.length)]) }

        repeat(lengthValue - (capitalValue + smallChars + digitsValue + symbolsValue)) {
            passwordBuilder.append(allCharacters[Random.nextInt(allCharacters.length)])
        }

        val shuffledPassword = passwordBuilder.toString().toCharArray().also { it.shuffle() }
        return String(shuffledPassword)
    }
}