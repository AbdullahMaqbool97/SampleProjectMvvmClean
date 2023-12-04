package com.example.retrofitsamplewithcleanarchitecture.domain.base

import com.example.retrofitsamplewithcleanarchitecture.util.ApiErrorHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseUseCase<Response>(
    private val apiErrorHandle: ApiErrorHandle? = null
) where Response : Any {

    abstract suspend fun run(parameter: Any?): Flow<Any>

    open fun invoke(
        scope: CoroutineScope,
        parameter: Any?,
        onResult: (UseCaseResponse<Response>)
    ) {
        val backgroundJob = scope.async {
            run(parameter)
        }
        scope.launch {
            try {
                backgroundJob.await().let {
                    it.catch { ex ->
                        ex.printStackTrace()
                        onResult.onError(apiErrorHandle?.traceErrorException(ex))
                    }.collect { response ->
                        onResult.onSuccess(response)
                    }
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                onResult.onError(apiErrorHandle?.traceErrorException(ex))
            }
        }
    }


}