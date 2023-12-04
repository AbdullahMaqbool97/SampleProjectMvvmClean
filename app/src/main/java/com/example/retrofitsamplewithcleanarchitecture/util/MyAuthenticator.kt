package com.example.retrofitsamplewithcleanarchitecture.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.retrofitsamplewithcleanarchitecture.data.remote.apiServices.ApiNetworkService
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MyAuthenticator @Inject constructor(
    context: Context
//    activity: Activity
) :
    Authenticator {

    private val mContext = context


    override fun authenticate(route: Route?, response: Response): Request? {

//         set maximum retry count
//        if (response.responseCount >= 3) {
//            Log.e("zaza-Authenticator", "max-fail")
//            return null // If we've failed 3 times, give up.
//        }

    Log.d("TAG", "authenticate: "+ response.responseCount)

        // write code to refresh the token
        /*val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("refreshToken", PreferenceHelper(mContext).refreshToken).build()
            chain.proceed(request)
        })
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        httpClient.addInterceptor(loggingInterceptor)
        Log.e("zaza-Authenticator", "API CAll")

        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL_AUTH)
                .client(
                    httpClient.readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES)
                        .build()
                ).build()
        val service = retrofit.create(ApiNetworkService::class.java)
        val authRequest = RefreshSessionRequest(PreferenceHelper(mContext).phoneNumber)
        val call: Call<RefreshSessionResponse> = service.refreshSession(authRequest)
        val res = call.execute()
        if (res.isSuccessful) {
            Constants.Authenticator.count = 0
            val data = res.body()
            val newAccessToken = data?.results?.data?.idToken ?: "" // your new token from response
            PreferenceHelper(mContext).authorizationToken = data?.results?.data?.idToken ?: ""
            Log.e("zaza-Authenticator", "success")

            val token = PreferenceHelper(mContext).authToken
            val tokenType = PreferenceHelper(mContext).tokenType
            val authorization = PreferenceHelper(mContext).authorizationToken

            val headers = response.headers.newBuilder()
                .add("Authorization", tokenType + " " + token)
                .add("idToken", authorization)
                .add("refreshToken", newAccessToken)
                .add("interface", "mobile")
                .build()

            return response.request
                .newBuilder()
                .headers(headers)
                .build()

        } else {
            Constants.Authenticator.count++
            return if (res.code() == 401) {
                Log.d("TAG", "authenticate: "+ response.responseCount)
                Constants.logOutReference?.let {
                    Constants.logOutReference!!.invoke()
                }
                null
            } else {
                null
            }
        }*/
        return null
    }

    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()


}