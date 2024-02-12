package dev.jay.passgenius.usecase

import android.content.res.Resources
import dev.jay.passgenius.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.util.Locale
import javax.inject.Inject

class GetWordsForPasswordUseCase @Inject constructor() {
    suspend fun getWords(): List<String> = withContext(Dispatchers.IO) {
        val wordList = mutableListOf<String>()
        try {
            val inputStream = Resources.getSystem().openRawResource(R.raw.words)
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
}