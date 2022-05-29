package id.novian.challengechapter7.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.novian.challengechapter7.helper.DataStoreManager
import id.novian.challengechapter7.model.network.client.ApiClient
import id.novian.challengechapter7.model.network.client.ApiService
import id.novian.challengechapter7.repository.LocalRepository
import id.novian.challengechapter7.repository.NetworkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) = DataStoreManager(context)

    @Provides
    fun provideLocalRepository(@ApplicationContext context: Context) = LocalRepository(context)

    @Singleton
    @Provides
    fun provideApiService() = ApiClient.instance

    @Singleton
    @Provides
    fun provideNetworkRepository(apiService: ApiService) = NetworkRepository(apiService)
}