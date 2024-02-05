package dev.jay.passgenius.usecase

import dev.jay.passgenius.database.repository.PasswordRoomDatabaseRepositoryImpl
import javax.inject.Inject

class FindPasswordsUseCase @Inject constructor(private val passwordRoomDatabaseRepositoryImpl: PasswordRoomDatabaseRepositoryImpl) {
    fun findPasswordsBySiteName(siteName: String) {
        passwordRoomDatabaseRepositoryImpl.findPasswordsBySiteName(siteName)
    }
}