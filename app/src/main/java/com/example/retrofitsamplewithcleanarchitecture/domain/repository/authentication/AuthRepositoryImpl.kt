package com.example.retrofitsamplewithcleanarchitecture.domain.repository.authentication


import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val onBoardingPrefsManager: OnBoardingPrefsManager
) : AuthRepository {


    override suspend fun saveUserPhoneNumberToDataStorePrefs(phone: String) {
        onBoardingPrefsManager.savePhoneToDataStore(phone)
        Timber.d("PHONE IN DATA_STORE: ${onBoardingPrefsManager.getUserPhoneNumber()}")
    }

    override suspend fun getPhoneNumberFromDataStore() : Flow<String?> {

        return onBoardingPrefsManager.phoneNumber
            .map { phoneNumber ->
                if(phoneNumber!!.isBlank()){
                    Timber.d("No PhoneNumber Found.")
                }
                phoneNumber.let {
                    return@let phoneNumber
                }
            }
    }

    override suspend fun saveUserDeviceNameToPrefs(deviceName: String) {
        onBoardingPrefsManager.saveDeviceNameToDataStore(deviceName)
    }

    override suspend fun saveVerificationStatusToDataStorePrefs(isVerified: Boolean) {
        onBoardingPrefsManager.saveNumberVerificationToDataStore(isVerified)
        Timber.d("ProfileStatus IN DATA_STORE: ${onBoardingPrefsManager.getPhoneNumberVerificationStatus()}")
    }

    override suspend fun saveProfileStatusToPrefs(isProfileCreated: Boolean) {
        onBoardingPrefsManager.saveProfileStatusToDataStore(isProfileCreated)
        Timber.d("ProfileStatus IN DATA_STORE: ${onBoardingPrefsManager.getProfileStatusToDataStore()}")
    }

    override suspend fun setBiometricsStatusToDataStorePrefs(isBiometricsEnabled: Boolean) {
        onBoardingPrefsManager.setBiometricsStatusToDataStore(isBiometricsEnabled)
//        Timber.d("ProfileStatus IN DATA_STORE: ${onBoardingPrefsManager.getBiometricsStatus()}")
    }

   /* override suspend fun setMpinToDataStorePrefs(mpin: String) {
        onBoardingPrefsManager.saveMpinToDataStore(mpin)
        Timber.d("ProfileStatus IN DATA_STORE: ${onBoardingPrefsManager.getMpinFromDataStore()}")
    }*/
}