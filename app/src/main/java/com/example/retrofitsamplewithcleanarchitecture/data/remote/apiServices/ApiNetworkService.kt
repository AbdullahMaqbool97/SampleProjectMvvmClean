package com.example.retrofitsamplewithcleanarchitecture.data.remote.apiServices

import com.example.retrofitsamplewithcleanarchitecture.data.remote.AuthRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.model.AuthTokenDto
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.AuthLoginRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.AuthRegistrationRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.notification.FCMRequest
import com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.token_response.TokenResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiNetworkService {

    @POST("oauth2/token")
    fun refreshToken(@Body params: AuthRequest): Call<TokenResponse>

    @POST("notify/v1/notifications/proxy-oAuth")
    suspend fun apimToken(@Body requestBody: AuthRequest): TokenResponse

    @POST("notify/v1/notifications/setFCM")
    fun sentFcmToken(@Body params: FCMRequest): Call<JsonObject>

    @POST("api/login")
    suspend fun login(@Body loginRequest: AuthLoginRequest): Response<AuthTokenDto>

    @POST("api/register")
    suspend fun register(@Body registrationRequest: AuthRegistrationRequest): Response<AuthTokenDto>
}