package dev.jay.passgenius.usecase

import dev.jay.passgenius.utils.PasswordUtility
import javax.inject.Inject

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
        return PasswordUtility.generateRobustPassword(lengthValue, capitalValue, digitsValue, symbolsValue)
    }
}