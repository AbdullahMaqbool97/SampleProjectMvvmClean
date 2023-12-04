package com.example.retrofitsamplewithcleanarchitecture.di

import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager
import com.example.retrofitsamplewithcleanarchitecture.data.remote.apiServices.ApiNetworkService
import com.example.retrofitsamplewithcleanarchitecture.domain.repository.apim.ApimTokenRepository
import com.example.retrofitsamplewithcleanarchitecture.domain.repository.apim.ApimTokenRepositoryImpl
import com.example.retrofitsamplewithcleanarchitecture.domain.repository.authentication.AuthRepository
import com.example.retrofitsamplewithcleanarchitecture.domain.repository.authentication.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        onBoardingPrefsManager: OnBoardingPrefsManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            onBoardingPrefsManager = onBoardingPrefsManager
        )
    }

    @Singleton
    @Provides
    fun provideApimTokenRepository(
        apiNetworkService: ApiNetworkService
    ): ApimTokenRepository {
        return ApimTokenRepositoryImpl(apiNetworkService)
    }
}