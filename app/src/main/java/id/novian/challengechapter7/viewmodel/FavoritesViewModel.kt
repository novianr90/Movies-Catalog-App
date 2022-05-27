package id.novian.challengechapter7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.challengechapter7.helper.Resource
import id.novian.challengechapter7.model.local.entity.FavoritesMovie
import id.novian.challengechapter7.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val localRepository: LocalRepository) :
    ViewModel() {
    fun insertFav(favoritesMovie: FavoritesMovie) {
        viewModelScope.launch {
            localRepository.insertFavorites(favoritesMovie)
        }
    }

    fun getFavorites(profileId: Int) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = localRepository.getFavoritesById(profileId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }
}