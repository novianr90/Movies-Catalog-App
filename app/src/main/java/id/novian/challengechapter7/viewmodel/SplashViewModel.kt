package id.novian.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.novian.challengechapter7.helper.DataStoreManager
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {
    fun checkStatusLogin(): LiveData<Boolean> {
        return dataStoreManager.getStatusLogin().asLiveData()
    }
}