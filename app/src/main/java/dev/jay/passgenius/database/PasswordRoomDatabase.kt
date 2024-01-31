package dev.jay.passgenius.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(PasswordModel::class)], version = 1, exportSchema = false)
abstract class PasswordRoomDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}