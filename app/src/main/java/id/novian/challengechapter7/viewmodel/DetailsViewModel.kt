package id.novian.challengechapter7.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.challengechapter7.helper.DataStoreManager
import id.novian.challengechapter7.helper.Resource
import id.novian.challengechapter7.model.local.entity.FavoritesMovie
import id.novian.challengechapter7.repository.LocalRepository
import id.novian.challengechapter7.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {
    fun getDetails(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = networkRepository.getDetails(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }

    fun insertFavorites(favorite: FavoritesMovie) {
        viewModelScope.launch {
            localRepository.insertFavorites(favorite)
        }
    }

    fun getEmail(): LiveData<String> {
        return dataStoreManager.getEmail().asLiveData()
    }
}