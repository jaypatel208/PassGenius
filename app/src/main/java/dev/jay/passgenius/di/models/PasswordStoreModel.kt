package dev.jay.passgenius.di.models

data class PasswordStoreModel(
    val id: Int,
    val site: String,
    val username: String,
    val password: String
)
