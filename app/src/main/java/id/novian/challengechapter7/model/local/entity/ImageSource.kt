package id.novian.challengechapter7.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageSource(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "profile_id") val profileId: Int,
    @ColumnInfo(name = "image_src") val imgSrc: String
)
