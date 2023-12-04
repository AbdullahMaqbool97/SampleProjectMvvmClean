package com.example.retrofitsamplewithcleanarchitecture.domain.repository.apim

import com.example.retrofitsamplewithcleanarchitecture.data.remote.AuthRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.token_response.TokenResponse
import kotlinx.coroutines.flow.Flow

interface ApimTokenRepository {
    fun getAccessToken(requestBody: AuthRequest): Flow<TokenResponse>
}