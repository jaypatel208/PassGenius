package dev.jay.passgenius.utils

import dev.jay.passgenius.di.models.CategoriesPasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStoreModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

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

    fun generateRobustPassword(lengthValue: Int, capitalValue: Int, digitsValue: Int, symbolsValue: Int): String {
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

    suspend fun generateMemorablePassword(wordList: List<String>, wordsAmount: Int, digitsAmount: Int): String =
        withContext(
            Dispatchers.IO
        ) {
            require(wordsAmount in 3..6) { "Number of words must be between 3 and 6 inclusive" }
            require(digitsAmount in 1..5) { "Number of digits must be between 1 and 5 inclusive" }

            val random = Random(System.currentTimeMillis())
            val selectedWords = (1..wordsAmount).map { wordList.random(random) }
            val selectedDigits = (1..digitsAmount).joinToString("") { random.nextInt(10).toString() }

            return@withContext selectedWords.joinToString("-") + "-" + selectedDigits
        }
}