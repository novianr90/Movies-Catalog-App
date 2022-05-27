package id.novian.challengechapter7.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.challengechapter7.helper.DataStoreManager
import id.novian.challengechapter7.helper.Resource
import id.novian.challengechapter7.repository.LocalRepository
import id.novian.challengechapter7.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private var _usernameDb = MutableLiveData<String>()
    val usernameDb: LiveData<String> get() = _usernameDb


    fun getEmail(): LiveData<String> {
        return dataStoreManager.getEmail().asLiveData()
    }

    fun getUsername(email: String) {
        viewModelScope.launch {
            _usernameDb.postValue(localRepository.getUsernameByEmail(email))
        }
    }


    fun getAllPopularMovies() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = networkRepository.getPopular()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred"))
        }
    }

}