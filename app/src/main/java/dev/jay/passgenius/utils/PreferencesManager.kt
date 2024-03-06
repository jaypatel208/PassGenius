package dev.jay.passgenius.utils

import android.content.Context

class PreferencesManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(Constants.Preferences.PASS_GENIUS, Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}