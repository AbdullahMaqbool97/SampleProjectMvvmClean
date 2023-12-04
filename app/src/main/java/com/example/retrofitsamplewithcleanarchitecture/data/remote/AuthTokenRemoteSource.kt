package com.example.retrofitsamplewithcleanarchitecture.data.remote

import com.example.retrofitsamplewithcleanarchitecture.data.remote.model.AuthTokenDto
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.AuthLoginRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.AuthRegistrationRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthTokenRemoteSource {

    suspend fun loginAuthToken(authLoginRequest: AuthLoginRequest): Flow<Response<AuthTokenDto>>

    suspend fun registerAuthToken(authRegistrationRequest: AuthRegistrationRequest): Flow<Response<AuthTokenDto>>
}