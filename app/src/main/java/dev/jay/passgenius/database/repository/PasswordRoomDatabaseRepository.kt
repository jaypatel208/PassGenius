package dev.jay.passgenius.database.repository

import dev.jay.passgenius.database.PasswordModel

interface PasswordRoomDatabaseRepository {
    fun savePassword(newPassword: PasswordModel)
    fun deletePassword(newPassword: PasswordModel)
    fun updatePassword(newPassword: PasswordModel)
}