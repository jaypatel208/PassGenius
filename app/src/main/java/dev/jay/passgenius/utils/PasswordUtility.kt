package dev.jay.passgenius.utils

import dev.jay.passgenius.di.models.CategoriesPasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStoreModel

object PasswordUtility {
    fun categorizePasswords(passwords: List<PasswordStoreModel>): List<CategoriesPasswordStoreModel> {
        val categorizedMap = mutableMapOf<Char, MutableList<PasswordStoreModel>>()

        for (password in passwords) {
            val firstAlphabet = password.site.first().uppercaseChar()
            categorizedMap.computeIfAbsent(firstAlphabet) { mutableListOf() }.add(password)
        }

        val sortedCategories = categorizedMap.entries.sortedBy { it.key }

        val categoriesList = sortedCategories.map { entry ->
            CategoriesPasswordStoreModel(
                alphabet = entry.key.toString(),
                items = entry.value.sortedBy { it.site }
            )
        }

        return categoriesList
    }
}