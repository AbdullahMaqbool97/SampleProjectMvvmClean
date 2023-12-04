package com.example.retrofitsamplewithcleanarchitecture.di

import android.content.Context
import android.util.Log
import com.example.retrofitsamplewithcleanarchitecture.BuildConfig
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager
import com.example.retrofitsamplewithcleanarchitecture.data.remote.apiServices.ApiNetworkService
import com.example.retrofitsamplewithcleanarchitecture.util.MyAuthenticator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.API_URL

    @Provides
    @Singleton
    fun provideGSON() = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        preferenceDataStore: OnBoardingPrefsManager,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val productionHost = "app.mc.mycashfs.com"
        val devBeHost = "dev-be.mc.mycashfs.com"
        val devHost = "dev.mc.mycashfs.com"
        val stagingHost = "mycashdev.mc.moncashdfs.com"    //"staging.mc.mycashfs.com"
        val preProdProductionHost = "preprod.mc.mycashfs.com"

        val certificatePinner = CertificatePinner.Builder()
            .add(
                productionHost,
                "sha256/TqqyRY1gkYKOZKQbihIAHaI1sp5bws+5HU5+bfU2Big="
            ) // old certificate expiring on 29 june 2023
            .add(
                productionHost,
                "sha256/GIIExTg3FczJyQ8CAQFdvr4PG1qhCsv0oi7JTVZ1Mxw="
            ) // new certificate with mc domain

            //.add(productionHost, "sha256/Ne0m77MufBxohPCZXn1quj9QIwZKOdJPBG/svcT+q64=") // new certificate with mc1 domain

            .add(
                devBeHost,
                "sha256/TqqyRY1gkYKOZKQbihIAHaI1sp5bws+5HU5+bfU2Big="
            ) // old certificate

            .add(devHost, "sha256/TqqyRY1gkYKOZKQbihIAHaI1sp5bws+5HU5+bfU2Big=")  // old certificate
            .add(devHost, "sha256/GIIExTg3FczJyQ8CAQFdvr4PG1qhCsv0oi7JTVZ1Mxw=") // new certificate

            .add(
                stagingHost,
                "sha256/TqqyRY1gkYKOZKQbihIAHaI1sp5bws+5HU5+bfU2Big="
            ) // old certificate
            .add(
                stagingHost,
                "sha256/GIIExTg3FczJyQ8CAQFdvr4PG1qhCsv0oi7JTVZ1Mxw="
            ) // new certificate with mc domain

            .add(
                stagingHost,
                "sha256/oTMvQarc64fRK8tD838k9FCed+D2ntaNshRXGtzhz6Q="
            ) // new certificate with moncashdfs domain

            .add(
                preProdProductionHost,
                "sha256/TqqyRY1gkYKOZKQbihIAHaI1sp5bws+5HU5+bfU2Big="
            ) // old certificate expiring on 29 june 2023
            .add(
                preProdProductionHost,
                "sha256/GIIExTg3FczJyQ8CAQFdvr4PG1qhCsv0oi7JTVZ1Mxw="
            ) // new certificate with mc domain

            .build()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(AuthorizationInterceptor(context, preferenceDataStore))
            .authenticator(MyAuthenticator(context))
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .serializeNulls()
                        .setLenient()
                        .create()
                )
            )
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    class AuthorizationInterceptor(
        var context: Context,
        private val preferenceHelper: OnBoardingPrefsManager
    ) :
        Interceptor {
        private var response: Response? = null

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val token = "PreferenceHelper(context).authToken"
            val tokenType = "PreferenceHelper(context).tokenType"
            val apimToken = "PreferenceHelper(context).apimToken"
            val refreshToken = "PreferenceHelper(context).refreshToken"
            val authorization = "PreferenceHelper(context).authorizationToken"
            val language = "PreferenceHelper(context).language"
            var request: Request = chain.request()
            if (request.headers["Authorization"] == null || request.headers["Authorization"]!!.isEmpty() || request.headers["idToken"] == null || request.headers["idToken"]!!.isEmpty() || request.headers["refreshToken"] == null || request.headers["refreshToken"]!!.isEmpty()) {
                val headers = request.headers.newBuilder()
                    .add("Authorization", "Bearer " + apimToken)
                    .add("idToken", authorization)
                    .add("refreshToken", refreshToken)
                    .add("interface", "mobile")
                    .add("language", language)
                    .build()
                Log.d("TAG", "intercept: " + apimToken)
                Log.d("TAG", "intercept2: " + authorization)
                Log.d("TAG", "intercept23: " + refreshToken)
                request = request.newBuilder().headers(headers).build()

            }
            response = chain.proceed(request)
            return response as Response
        }
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiNetworkService =
        retrofit.create(ApiNetworkService::class.java)

}