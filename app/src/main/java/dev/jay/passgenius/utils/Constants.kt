package dev.jay.passgenius.utils

object Constants {
    const val HOME = "Home"
    const val SECURITY_AUDIT = "Security Audit"
    const val PASSWORD_GENERATE = "Password Generate"
    const val SETTINGS = "Settings"
    const val LIMIT_CHAR_SITE_NAME = 30
    const val LIMIT_CHAR_PASSWORD = 45

    object NavArgs {
        const val PASSWORD_ID = "password_id"
        const val SITE_NAME = "site_name"
        const val USER_NAME = "user_name"
        const val PASSWORD = "password"
    }

    object Preferences{
        const val AUTH_STATUS = "auth_status"
        const val PASS_GENIUS = "pass_genius"
        const val AUTH_TRUE = "auth_true"
        const val AUTH_FALSE = "auth_false"
        const val DEFAULT_VAL = "default_value"
        const val PIN = "login_pin"
    }
}