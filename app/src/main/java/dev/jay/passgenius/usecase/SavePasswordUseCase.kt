package dev.jay.passgenius.usecase

import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.database.repository.PasswordRoomDatabaseRepositoryImpl
import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(private val passwordRoomDatabaseRepositoryImpl: PasswordRoomDatabaseRepositoryImpl) {
    fun savePassword(passwordModel: PasswordModel) {
        passwordRoomDatabaseRepositoryImpl.savePassword(passwordModel)
    }
}