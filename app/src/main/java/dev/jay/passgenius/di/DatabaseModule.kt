package dev.jay.passgenius.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jay.passgenius.database.PasswordDao
import dev.jay.passgenius.database.PasswordRoomDatabase
import dev.jay.passgenius.database.repository.PasswordRoomDatabaseRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providePasswordDao(passwordRoomDatabase: PasswordRoomDatabase) = passwordRoomDatabase.passwordDao()

    @Singleton
    @Provides
    fun providePasswordRoomDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        PasswordRoomDatabase::class.java,
        "password_database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providePasswordRoomDatabaseRepository(passwordDao: PasswordDao) =
        PasswordRoomDatabaseRepositoryImpl(passwordDao)
}
