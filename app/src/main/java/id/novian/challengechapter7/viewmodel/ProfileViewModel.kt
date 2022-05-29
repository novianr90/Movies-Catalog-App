package id.novian.challengechapter7.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.challengechapter7.helper.DataStoreManager
import id.novian.challengechapter7.helper.Resource
import id.novian.challengechapter7.model.local.entity.ImageSource
import id.novian.challengechapter7.model.local.entity.Profile
import id.novian.challengechapter7.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private var _imgSrc = MutableLiveData<String>()
    val imgSrc: LiveData<String> get() = _imgSrc

    fun getDataProfile(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = localRepository.getProfile(email)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error Occurred"))
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            localRepository.updateProfile(profile)
        }
    }

    fun insertImageSrc(imageSource: ImageSource) {
        viewModelScope.launch {
            localRepository.insertImageSrc(imageSource)
        }
    }

    fun getImageSrc(email: String) {
        viewModelScope.launch {
            _imgSrc.postValue(localRepository.getImageByEmail(email))
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.saveStatusLogout()
        }
    }
}