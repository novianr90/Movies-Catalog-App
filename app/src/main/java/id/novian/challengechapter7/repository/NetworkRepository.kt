package id.novian.challengechapter7.repository

import id.novian.challengechapter7.BuildConfig
import id.novian.challengechapter7.model.network.client.ApiService
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService) {
    private val apiKey = BuildConfig.API_KEY

    suspend fun getPopular() = apiService.getMoviePopular(apiKey)
    suspend fun getDetails(id: Int) = apiService.getDetails(apiKey, id)
}