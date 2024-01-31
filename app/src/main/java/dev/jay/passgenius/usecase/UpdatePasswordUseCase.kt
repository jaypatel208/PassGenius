package dev.jay.passgenius.usecase

import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.database.repository.PasswordRoomDatabaseRepositoryImpl
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(private val passwordRoomDatabaseRepositoryImpl: PasswordRoomDatabaseRepositoryImpl) {
    fun updatePassword(passwordModel: PasswordModel) {
        passwordRoomDatabaseRepositoryImpl.updatePassword(passwordModel)
    }
}