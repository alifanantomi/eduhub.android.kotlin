package com.example.eduhub.di

import com.example.eduhub.data.api.ApiService
import com.example.eduhub.data.local.preferences.UserPreferences
import com.example.eduhub.data.repository.AuthRepository
import com.example.eduhub.data.repository.AuthRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        userPreferences: UserPreferences
    ): AuthRepositoryInterface {
        return AuthRepository(apiService, userPreferences)
    }
}