package dev.jay.passgenius.utils

import android.content.Context
import dev.jay.passgenius.di.models.CategoriesPasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStoreModel
import java.io.IOException

object PasswordUtility {
    fun readWordsFromFile(resourceId: Int, context: Context): List<String> = try {
        context.resources.openRawResource(resourceId).bufferedReader().useLines { lines ->
            lines.flatMap { line ->
                line.split("\\s+".toRegex())
                    .filter { it.length in 3..5 && it.matches(Regex("[a-zA-Z]+")) }
            }.toList()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        emptyList()
    }

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