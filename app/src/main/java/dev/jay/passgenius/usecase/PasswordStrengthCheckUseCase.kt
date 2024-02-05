package dev.jay.passgenius.usecase

import dev.jay.passgenius.di.models.PasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStrengthResult
import javax.inject.Inject

class PasswordStrengthCheckUseCase @Inject constructor() {
    fun checkPasswordStrength(passwords: List<PasswordStoreModel>): PasswordStrengthResult {
        val strongPasswords = mutableListOf<PasswordStoreModel>()
        val mediocrePasswords = mutableListOf<PasswordStoreModel>()

        for (passwordModel in passwords) {
            val password = passwordModel.password

            val lengthCheck = password.length >= 12

            val uppercaseCheck = password.any { it.isUpperCase() }

            val digitCheck = password.any { it.isDigit() }

            val symbolCheck = password.any { it.isLetterOrDigit().not() }

            if (lengthCheck && uppercaseCheck && digitCheck && symbolCheck) {
                strongPasswords.add(passwordModel)
            } else {
                mediocrePasswords.add(passwordModel)
            }
        }

        return PasswordStrengthResult(strongPasswords, mediocrePasswords)
    }
}