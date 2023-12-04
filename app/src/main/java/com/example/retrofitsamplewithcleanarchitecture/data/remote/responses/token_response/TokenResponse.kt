package com.example.retrofitsamplewithcleanarchitecture.data.remote.responses.token_response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("results") var results: Results? = Results(),
)

data class Data(
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("scope") var scope: String? = null,
    @SerializedName("token_type") var tokenType: String? = null,
    @SerializedName("expires_in") var expiresIn: Int? = null
)


data class Results(
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: Data? = Data()
)