package dev.jay.passgenius.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePassword(passwordModel: PasswordModel)

    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): List<PasswordModel>

    @Update
    suspend fun updatePassword(passwordModel: PasswordModel)

    @Delete
    suspend fun deletePassword(passwordModel: PasswordModel)

    @Query("SELECT * FROM passwords WHERE site = :siteName")
    fun findPasswordsBySiteName(siteName: String): List<PasswordModel>
}