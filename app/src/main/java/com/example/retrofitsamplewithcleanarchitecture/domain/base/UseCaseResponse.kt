package com.example.retrofitsamplewithcleanarchitecture.domain.base

import com.example.retrofitsamplewithcleanarchitecture.util.ErrorModel

interface UseCaseResponse<Type> {

    suspend fun onSuccess(result: Any)

    suspend fun onError(errorModel: ErrorModel?)
}
