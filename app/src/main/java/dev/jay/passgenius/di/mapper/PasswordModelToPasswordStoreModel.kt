package dev.jay.passgenius.di.mapper

import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.di.models.PasswordStoreModel

class PasswordModelToPasswordStoreModel : Mapper<PasswordModel, PasswordStoreModel> {
    override fun mapFrom(from: PasswordModel): PasswordStoreModel {
        return PasswordStoreModel(id = from.id, site = from.site, username = from.username, password = from.password)
    }
}