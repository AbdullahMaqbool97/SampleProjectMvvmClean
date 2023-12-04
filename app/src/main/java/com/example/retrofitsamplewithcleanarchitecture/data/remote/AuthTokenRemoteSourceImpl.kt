package com.example.retrofitsamplewithcleanarchitecture.data.remote

import com.example.retrofitsamplewithcleanarchitecture.data.remote.apiServices.ApiNetworkService
import com.example.retrofitsamplewithcleanarchitecture.data.remote.model.AuthTokenDto
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.AuthLoginRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.AuthRegistrationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class AuthTokenRemoteSourceImpl @Inject constructor(
    private val authServiceApi: ApiNetworkService
) : AuthTokenRemoteSource {

    override suspend fun loginAuthToken(authLoginRequest: AuthLoginRequest): Flow<Response<AuthTokenDto>> =
        flow {
            val result = authServiceApi.login(authLoginRequest)
            Timber.d("loginAuthToken impl: ${result.code()}")
            Timber.d("loginAuthToken impl: ${result.message()}")
            Timber.d("loginAuthToken impl: ${result.body()}")
            Timber.d("loginAuthToken impl: ${result.raw()}")
            Timber.d("loginAuthToken impl: ${result.isSuccessful}")
            Timber.d("loginAuthToken impl: ${result.errorBody()}")
            emit(result)
        }

    override suspend fun registerAuthToken(authRegistrationRequest: AuthRegistrationRequest): Flow<Response<AuthTokenDto>> =
        flow {
            val result = authServiceApi.register(authRegistrationRequest)
            Timber.d("loginAuthToken impl: ${result.code()}")
            Timber.d("loginAuthToken impl: ${result.message()}")
            Timber.d("loginAuthToken impl: ${result.body()}")
            Timber.d("loginAuthToken impl: ${result.raw()}")
            Timber.d("loginAuthToken impl: ${result.isSuccessful}")
            Timber.d("loginAuthToken impl: ${result.errorBody()}")
            emit(result)
        }

}