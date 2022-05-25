package id.novian.challengechapter7.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesMovie(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "profile_id") val profileId: Int,
    @ColumnInfo(name = "movie_id") val movie_id: Int
)