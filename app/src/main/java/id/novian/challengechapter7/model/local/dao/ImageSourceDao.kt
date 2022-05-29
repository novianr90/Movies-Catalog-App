package id.novian.challengechapter7.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.novian.challengechapter7.model.local.entity.ImageSource

@Dao
interface ImageSourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSrc(imageSource: ImageSource): Long

    @Query("SELECT image_src FROM ImageSource WHERE profile_email = :email")
    fun getImageByEmail(email: String): String
}