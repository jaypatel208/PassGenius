package dev.jay.passgenius.database.repository

import dev.jay.passgenius.database.PasswordDao
import dev.jay.passgenius.database.PasswordModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordRoomDatabaseRepositoryImpl(private val passwordDao: PasswordDao) : PasswordRoomDatabaseRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun savePassword(newPassword: PasswordModel) {
        coroutineScope.launch(Dispatchers.IO) {
            passwordDao.savePassword(newPassword)
        }
    }

    override fun deletePassword(newPassword: PasswordModel) {
        coroutineScope.launch(Dispatchers.IO) {
            passwordDao.deletePassword(newPassword)
        }
    }

    override fun updatePassword(newPassword: PasswordModel) {
        coroutineScope.launch(Dispatchers.IO) {
            passwordDao.updatePassword(newPassword)
        }
    }
}