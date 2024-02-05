package dev.jay.passgenius.database.repository

import dev.jay.passgenius.database.PasswordDao
import dev.jay.passgenius.database.PasswordModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

    override fun getAllPassword(): List<PasswordModel> {
        return runBlocking {
            async(Dispatchers.IO) {
                passwordDao.getAllPasswords()
            }.await()
        }
    }

    override fun findPasswordsBySiteName(siteName: String): List<PasswordModel> {
        return runBlocking {
            async(Dispatchers.IO) {
                passwordDao.findPasswordsBySiteName(siteName)
            }.await()
        }
    }

}