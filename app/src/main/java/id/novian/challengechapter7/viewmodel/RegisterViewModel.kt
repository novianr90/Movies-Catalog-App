package id.novian.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.challengechapter7.helper.DataStoreManager
import id.novian.challengechapter7.model.local.entity.Profile
import id.novian.challengechapter7.repository.LocalRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private var _dataSuccess = MutableLiveData<Boolean>()
    val dataSuccess: LiveData<Boolean> get() = _dataSuccess

    fun checkEmailAndUsername(email: String, username: String) {
        viewModelScope.launch {
            val emailDb = localRepository.getEmail(email)
            val usernameDb = localRepository.getUsername(username)

            if (emailDb.isNullOrEmpty() || usernameDb.isNullOrEmpty()) {
                _dataSuccess.postValue(true)
            } else {
                _dataSuccess.postValue(false)
            }
        }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch {
            dataStoreManager.saveEmail(email)
        }
    }

    fun insertProfile(profile: Profile) {
        viewModelScope.launch {
            localRepository.insertProfile(profile)
        }
    }

    fun setStatusLogin() {
        viewModelScope.launch {
            dataStoreManager.saveStatusLogin()
        }
    }
}