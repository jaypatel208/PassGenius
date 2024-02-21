package dev.jay.passgenius.usecase

import android.content.Context
import dev.jay.passgenius.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

class GetWordsForPasswordUseCase @Inject constructor() {
    private suspend fun getWords(context: Context): List<String> = withContext(Dispatchers.IO) {
        val wordList = mutableListOf<String>()
        try {
            val inputStream = context.resources.openRawResource(R.raw.words)
            val reader = BufferedReader(inputStream.reader())

            reader.useLines { lines ->
                lines.forEach { line ->
                    if (line.trim().length > 3) {
                        wordList.add(
                            line.trim()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return@withContext wordList
    }

    suspend fun generateMemorablePassword(context: Context, wordsAmount: Int, digitsAmount: Int): String =
        withContext(
            Dispatchers.IO
        ) {
            require(wordsAmount in 3..6) { "Number of words must be between 3 and 6 inclusive" }
            require(digitsAmount in 1..5) { "Number of digits must be between 1 and 5 inclusive" }

            val wordList = getWords(context)

            val random = Random(System.currentTimeMillis())
            val selectedWords = (1..wordsAmount).map { wordList.random(random) }
            val selectedDigits = (1..digitsAmount).joinToString("") { random.nextInt(10).toString() }

            return@withContext selectedWords.joinToString("-") + "-" + selectedDigits
        }
}