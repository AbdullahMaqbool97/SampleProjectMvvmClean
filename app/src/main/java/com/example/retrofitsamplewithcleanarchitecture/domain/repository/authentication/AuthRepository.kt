package com.example.retrofitsamplewithcleanarchitecture.domain.repository.authentication

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun saveUserPhoneNumberToDataStorePrefs(phoneNumber:String)

    suspend fun getPhoneNumberFromDataStore(): Flow<String?>

    suspend fun saveUserDeviceNameToPrefs(deviceName: String)

    suspend fun saveVerificationStatusToDataStorePrefs(isVerified: Boolean)

    suspend fun saveProfileStatusToPrefs(isProfileCreated: Boolean)

    suspend fun setBiometricsStatusToDataStorePrefs(isBiometricsEnabled: Boolean)

    //suspend fun setMpinToDataStorePrefs(mpin: String)


}