package id.novian.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.challengechapter7.helper.DataStoreManager
import id.novian.challengechapter7.repository.LocalRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private var _dataSuccess = MutableLiveData<Boolean>()
    val dataSuccess: LiveData<Boolean> get() = _dataSuccess

    fun checkEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val emailDb = localRepository.getEmail(email)
            val passDb = localRepository.getPassword(password)

            if (!emailDb.isNullOrEmpty() && !passDb.isNullOrEmpty()) {
                _dataSuccess.postValue(true)
                dataStoreManager.saveEmail(email)
            } else {
                _dataSuccess.postValue(false)
            }
        }
    }

    fun saveStatusLogin() {
        viewModelScope.launch {
            dataStoreManager.saveStatusLogin()
        }
    }
}