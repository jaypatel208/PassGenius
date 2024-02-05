package dev.jay.passgenius.di.models

data class PasswordStrengthResult(
    val strongPasswords: List<PasswordStoreModel>,
    val mediocrePasswords: List<PasswordStoreModel>
)
