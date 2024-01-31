package dev.jay.passgenius.usecase

import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.database.repository.PasswordRoomDatabaseRepositoryImpl
import javax.inject.Inject

class DeletePasswordUseCase @Inject constructor(private val passwordRoomDatabaseRepositoryImpl: PasswordRoomDatabaseRepositoryImpl) {
    fun deletePassword(passwordModel: PasswordModel) {
        passwordRoomDatabaseRepositoryImpl.deletePassword(passwordModel)
    }
}