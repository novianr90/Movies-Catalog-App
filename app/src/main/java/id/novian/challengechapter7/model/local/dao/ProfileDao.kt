package id.novian.challengechapter7.model.local.dao

import androidx.room.*
import id.novian.challengechapter7.model.local.entity.Profile

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile): Long

    @Update
    fun updateProfile(profile: Profile): Int

    @Query("SELECT * FROM PROFILE WHERE EMAIL = :email")
    fun getProfile(email: String): Profile

    @Query("SELECT email FROM Profile WHERE email = :email")
    fun getEmail(email: String): String

    @Query("SELECT password FROM Profile WHERE password = :pass")
    fun getPassword(pass: String): String

    @Query("SELECT username FROM Profile WHERE email = :email")
    fun getUsernameByEmail(email: String): String

    @Query("SELECT username FROM Profile WHERE username = :username")
    fun getUsername(username: String): String
}