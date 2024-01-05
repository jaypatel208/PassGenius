package dev.jay.passgenius.utils

import android.content.Context
import dev.jay.passgenius.models.CategoriesPasswordStoreModel
import dev.jay.passgenius.models.PasswordStoreModel
import java.io.IOException
import kotlin.random.Random

object PasswordUtility {
    private val symbolList = listOf("#", "$", "%", "@", "!", "&", "*")
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

    private fun generateRandomNumber(maxValue: Int): Int {
        require(maxValue > 0) { "Max value must be greater than 0" }
        return Random.nextInt(1, maxValue + 1)
    }

    private fun getRandomSymbolFromList(symbolList: List<String>): String {
        return symbolList[generateRandomNumber(symbolList.size - 1)]
    }

    private fun changeWordFormat(string: String): String {
        require(string.isNotBlank()) { "String can't be null or blank for operation" }
        return string.substring(0, 1).uppercase() + string.substring(1).lowercase()
    }

    fun generatePassword(wordList: List<String>): String {
        val words = wordList.shuffled()
        val word1 = changeWordFormat(words[0])
        val word2 = changeWordFormat(words[1])
        val word3 = changeWordFormat(words[2])

        val number1 = generateRandomNumber(9)
        val number2 = generateRandomNumber(9)

        val symbol = getRandomSymbolFromList(symbolList)

        return "$word1$symbol$word2$symbol$word3$symbol$number1$number2"
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