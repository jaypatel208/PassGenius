package dev.jay.passgenius.ui.navigation

object Routes {
    const val HOME_SCREEN = "home"
    const val HOME_SCREEN_NO_PASS = "home_no_pass"
    const val PASSWORD_GENERATE = "password generate"
    const val SECURITY_AUDIT = "security audit"
    const val SETTINGS = "settings"

    object PasswordGenerate {
        const val PASSWORD_ROBUST_CHOICE = "password robust choice"
        const val PASSWORD_MEMORABLE_CHOICE = "password memorable choice"
        const val PASSWORD_SAVE = "password save"
    }

    object HomeScreen{
        const val EDIT_PASSWORD = "edit_password"
        const val SEARCH = "search"
    }
}