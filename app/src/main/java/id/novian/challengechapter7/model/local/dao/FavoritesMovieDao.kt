package id.novian.challengechapter7.model.local.dao

import androidx.room.*
import id.novian.challengechapter7.model.local.entity.FavoritesMovie

@Dao
interface FavoritesMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favoritesMovie: FavoritesMovie): Long

    @Delete
    fun deleteFavorites(favoritesMovie: FavoritesMovie): Int

    @Query("SELECT movie_id FROM FavoritesMovie WHERE profile_email = :email")
    fun getFavoritesByEmail(email: String): Int
}