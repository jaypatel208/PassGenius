package dev.jay.passgenius.usecase

import dev.jay.passgenius.database.repository.PasswordRoomDatabaseRepositoryImpl
import dev.jay.passgenius.di.mapper.PasswordModelToPasswordStoreModel
import dev.jay.passgenius.di.models.PasswordStoreModel
import javax.inject.Inject

class GetAllPasswordsUseCase @Inject constructor(private val passwordRoomDatabaseRepositoryImpl: PasswordRoomDatabaseRepositoryImpl) {
    fun getAllPassword(): List<PasswordStoreModel> {
        return passwordRoomDatabaseRepositoryImpl.getAllPassword()
            .map { PasswordModelToPasswordStoreModel().mapFrom(it) }
    }
}