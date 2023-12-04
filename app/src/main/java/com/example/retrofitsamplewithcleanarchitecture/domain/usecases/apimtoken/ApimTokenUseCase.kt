package com.example.retrofitsamplewithcleanarchitecture.domain.usecases.apimtoken

import com.example.retrofitsamplewithcleanarchitecture.data.remote.AuthRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.token_response.TokenResponse
import com.example.retrofitsamplewithcleanarchitecture.domain.base.BaseUseCase
import com.example.retrofitsamplewithcleanarchitecture.domain.repository.apim.ApimTokenRepository

import com.example.retrofitsamplewithcleanarchitecture.util.ApiErrorHandle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApimTokenUseCase @Inject constructor(
    private val feedbackRepository: ApimTokenRepository, apiErrorHandle: ApiErrorHandle
) : BaseUseCase<TokenResponse>(apiErrorHandle = apiErrorHandle) {

    override suspend fun run(parameter: Any?): Flow<Any> {
        return feedbackRepository.getAccessToken(parameter as AuthRequest)
    }

}