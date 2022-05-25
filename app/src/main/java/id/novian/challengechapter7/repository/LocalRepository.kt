package id.novian.challengechapter7.repository

import android.content.Context
import id.novian.challengechapter7.model.local.LocalDatabase
import id.novian.challengechapter7.model.local.entity.FavoritesMovie
import id.novian.challengechapter7.model.local.entity.ImageSource
import id.novian.challengechapter7.model.local.entity.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepository(context: Context) {

    private val db = LocalDatabase.getInstance(context)

    //Start Profile
    suspend fun insertProfile(profile: Profile) = withContext(Dispatchers.IO) {
        db?.profileDao()?.insertProfile(profile)
    }

    suspend fun updateProfile(profile: Profile) = withContext(Dispatchers.IO) {
        db?.profileDao()?.updateProfile(profile)
    }

    suspend fun getProfile(email: String) = withContext(Dispatchers.IO) {
        db?.profileDao()?.getProfile(email)
    }

    suspend fun getEmail(email: String) = withContext(Dispatchers.IO) {
        db?.profileDao()?.getEmail(email)
    }

    suspend fun getPassword(password: String) = withContext(Dispatchers.IO) {
        db?.profileDao()?.getPassword(password)
    }

    suspend fun getUsername(email: String) = withContext(Dispatchers.IO) {
        db?.profileDao()?.getUsername(email)
    }
    //End Profile


    //Start Image Source
    suspend fun insertImageSrc(imageSource: ImageSource) = withContext(Dispatchers.IO) {
        db?.imageSourceDao()?.insertSrc(imageSource)
    }

    suspend fun getImageById(profileId: Int) = withContext(Dispatchers.IO) {
        db?.imageSourceDao()?.getImageById(profileId)
    }
    //End Image Source


    //Start Favorites Movie
    suspend fun insertFavorites(favoritesMovie: FavoritesMovie) = withContext(Dispatchers.IO) {
        db?.favoritesMovieDao()?.insertFavorites(favoritesMovie)
    }

    suspend fun deleteFavorites(favoritesMovie: FavoritesMovie) = withContext(Dispatchers.IO) {
        db?.favoritesMovieDao()?.deleteFavorites(favoritesMovie)
    }

    suspend fun getFavoritesById(profileId: Int) = withContext(Dispatchers.IO) {
        db?.favoritesMovieDao()?.getFavoritesById(profileId)
    }
    //End Favorites Movie
}