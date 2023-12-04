package com.example.retrofitsamplewithcleanarchitecture.domain.repository.apim

import com.example.retrofitsamplewithcleanarchitecture.data.remote.AuthRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.apiServices.ApiNetworkService
import com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.token_response.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApimTokenRepositoryImpl @Inject constructor(
    private val apiNetworkService: ApiNetworkService
) : ApimTokenRepository {

    override fun getAccessToken(requestBody: AuthRequest): Flow<TokenResponse> {
        return flow {
            emit(
                apiNetworkService.apimToken(
                    requestBody
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}