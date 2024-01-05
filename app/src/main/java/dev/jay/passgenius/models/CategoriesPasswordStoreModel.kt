package dev.jay.passgenius.models

data class CategoriesPasswordStoreModel(
    val alphabet: String,
    val items: List<PasswordStoreModel>
)
