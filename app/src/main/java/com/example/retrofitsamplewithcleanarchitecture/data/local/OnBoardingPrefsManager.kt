package com.example.retrofitsamplewithcleanarchitecture.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.ACCOUNT_STATUS
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.ALREADY_RATED_STATUS
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.APIM_TOKEN
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.AUTH_TOKEN
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.AUTH_TOKEN_EXPIRE_TIME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.AUTH_TOKEN_TYPE
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.DOB
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.EMAIL_ID
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.FCM_TOKEN
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.FIRST_NAME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.FULL_ADDRESS
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.FULL_NAME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.HUAWEI_STATUS
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.IS_BIOMETRICS_ENABLED
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.IS_NUMBER_VERIFIED
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.IS_PROFILE_EXISTED
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.IS_UPGRADE_TO_MAX_ALLOWED
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.IV_BIOMETRICS
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.LAST_NAME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.LOCAL_LANGUAGE
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.MAX_ACCOUNT
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.MPIN_BIOMETRICS
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.NEVER_US_TIME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.NOTIFICATION_COUNT
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.PARISH
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.PENDING_INVITES
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.PIN_STATUS
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.PROFILE_IMAGE
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.PROFILE_PIC
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.RATE_US_TIME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.SET_TUTORIAL_VERSION
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.SHOW_TUTORIAL
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.SOURCE_OF_INCOME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.TOWN
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.TRN_NUMBER
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.UPGRADE_TO_MAX_IN_PROCESS_MSG
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.UPGRADE_TO_MAX_PROMPT_MSG
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.USER_BALANCE
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.USER_DEVICE_NAME
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.USER_ID
import com.example.retrofitsamplewithcleanarchitecture.data.local.OnBoardingPrefsManager.PreferencesKeys.USER_PHONE_NUMBER
import com.example.retrofitsamplewithcleanarchitecture.data.local.model.UserModel
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.ACCOUNT_STATUS_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.ADDRESS_FULL
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.ALREADY_RATED
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.APIMTOKEN
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.AUTH_TOKEN_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.AUTH_TOKEN_TIME_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.AUTH_TOKEN_TYPE_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.AUTO_AUTH_PREFS
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.BIOMETRICS_ENABLED
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.COUNT_NOTIFICATION
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.DOB_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.EMAIL_ADDRESS
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.FCM_TOKEN_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.FIRST_NAME_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.FULL_NAME_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.HUAWEI_STATUS_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.INCOME_SOURCE
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.IS_MAX_ACCOUNT
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.IS_UPGRADE_MAX_ALLOWED
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.IV_BIO
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.LANGUAGE
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.LAST_NAME_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.LAST_RATE_US_TIME
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.MPIN_BIOMETRIC
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.NEVER_RATE_US
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.NUMBER_VERIFIED
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.PARISH_
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.PIN_STATUS_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.PREF_PENDING_INVITES
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.PROFILE_IMAGE_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.PROFILE_IMG
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.SHOW_TUTORIAL_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.TOWN_
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.TRN
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.TUTORIAL_VERSION
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.UPDATED_TUTORIAL_VERSION
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.UPGRADE_MAX_MESSAGE
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.UPGRADE_MAX_PROMPT_MESSAGE
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.USER_BALANCE_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.USER_DEVICE_NAME_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.USER_ID_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.USER_PHONE_NUMBER_PREF
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.USER_PROFILE_EXISTED
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class OnBoardingPrefsManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore(name = AUTO_AUTH_PREFS)

    val phoneNumber: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_PHONE_NUMBER]
        }


    private object PreferencesKeys {
        val USER_PHONE_NUMBER = preferencesKey<String>(USER_PHONE_NUMBER_PREF)
        val AUTH_TOKEN = preferencesKey<String>(AUTH_TOKEN_PREF)
        val PROFILE_IMAGE = preferencesKey<String>(PROFILE_IMAGE_PREF)
        val FCM_TOKEN = preferencesKey<String>(FCM_TOKEN_PREF)
        val AUTH_TOKEN_TYPE = preferencesKey<String>(AUTH_TOKEN_TYPE_PREF)
        val USER_DEVICE_NAME = preferencesKey<String>(USER_DEVICE_NAME_PREF)
        val USER_ID = preferencesKey<String>(USER_ID_PREF)
        val USER_BALANCE = preferencesKey<String>(USER_BALANCE_PREF)
        val ACCOUNT_STATUS = preferencesKey<String>(ACCOUNT_STATUS_PREF)
        val PIN_STATUS = preferencesKey<String>(PIN_STATUS_PREF)
        val HUAWEI_STATUS = preferencesKey<String>(HUAWEI_STATUS_PREF)
        val FULL_NAME = preferencesKey<String>(FULL_NAME_PREF)
        val FIRST_NAME = preferencesKey<String>(FIRST_NAME_PREF)
        val LAST_NAME = preferencesKey<String>(LAST_NAME_PREF)
        val DOB = preferencesKey<String>(DOB_PREF)
        val SHOW_TUTORIAL = preferencesKey<String>(SHOW_TUTORIAL_PREF)
        val IS_NUMBER_VERIFIED = preferencesKey<Boolean>(NUMBER_VERIFIED)
        val IS_PROFILE_EXISTED = preferencesKey<Boolean>(USER_PROFILE_EXISTED)
        val IS_BIOMETRICS_ENABLED = preferencesKey<Boolean>(BIOMETRICS_ENABLED)
        val AUTH_TOKEN_EXPIRE_TIME = preferencesKey<String>(AUTH_TOKEN_TIME_PREF)
        val MPIN_BIOMETRICS = preferencesKey<String>(MPIN_BIOMETRIC)
        val IV_BIOMETRICS = preferencesKey<String>(IV_BIO)
        val RATE_US_TIME = preferencesKey<Long>(LAST_RATE_US_TIME)
        val NEVER_US_TIME = preferencesKey<Boolean>(NEVER_RATE_US)

         val ALREADY_RATED_STATUS = preferencesKey<Boolean>(ALREADY_RATED)
        val SET_TUTORIAL_VERSION = preferencesKey<Int>(TUTORIAL_VERSION)
         val SET_UPDATED_TUTORIAL_VERSION = preferencesKey<Int>(UPDATED_TUTORIAL_VERSION)
        val TRN_NUMBER = preferencesKey<String>(TRN)

        val PROFILE_PIC = preferencesKey<String>(PROFILE_IMG)
        val EMAIL_ID = preferencesKey<String>(EMAIL_ADDRESS)
        val SOURCE_OF_INCOME = preferencesKey<String>(INCOME_SOURCE)
        val FULL_ADDRESS = preferencesKey<String>(ADDRESS_FULL)
        val PARISH = preferencesKey<String>(PARISH_)
        val TOWN = preferencesKey<String>(TOWN_)
        val PENDING_INVITES = preferencesKey<String>(PREF_PENDING_INVITES)

        val MAX_ACCOUNT = preferencesKey<String>(IS_MAX_ACCOUNT)

        val NOTIFICATION_COUNT = preferencesKey<Int>(COUNT_NOTIFICATION)

        val IS_UPGRADE_TO_MAX_ALLOWED = preferencesKey<Boolean>(IS_UPGRADE_MAX_ALLOWED)
        val UPGRADE_TO_MAX_IN_PROCESS_MSG = preferencesKey<String>(UPGRADE_MAX_MESSAGE)
        val UPGRADE_TO_MAX_PROMPT_MSG = preferencesKey<String>(UPGRADE_MAX_PROMPT_MESSAGE)
        val APIM_TOKEN = preferencesKey<String>(APIMTOKEN)
        val LOCAL_LANGUAGE = preferencesKey<String>(LANGUAGE)

    }

    suspend fun saveUserObject(userModel: UserModel) {
        saveProfileImg(userModel.profileImg.toString())
        saveEmailId(userModel.email.toString())
        saveSourceOfIncome(userModel.sourceOfIncome.toString())
        saveFullAddress(userModel.fullAddress.toString())
        saveParish(userModel.parish.toString())
        saveTown(userModel.town.toString())
    }

    suspend fun getUserObject(): UserModel {
        val preferences = dataStore.data.first()
        return UserModel(
            preferences[EMAIL_ID],
            preferences[PROFILE_PIC], preferences[SOURCE_OF_INCOME],
            preferences[FULL_ADDRESS], preferences[PARISH],
            preferences[TOWN]
        )
    }


    suspend fun saveEmailId(email: String) {
        dataStore.edit { _dataStore ->
            _dataStore[EMAIL_ID] = email
        }
    }

    suspend fun getEmailId(): String? {
        val preferences = dataStore.data.first()
        return preferences[EMAIL_ID]
    }


    suspend fun saveTrn(trn: String) {
        dataStore.edit { _dataStore ->
            _dataStore[TRN_NUMBER] = trn
        }
    }

    suspend fun getTrn(): String? {
        val preferences = dataStore.data.first()
        return preferences[TRN_NUMBER]
    }

    suspend fun saveApimToken(token: String) {
        dataStore.edit { _dataStore ->
            _dataStore[APIM_TOKEN] = token
        }
    }

    suspend fun getApimToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[APIM_TOKEN]
    }

    suspend fun saveNotificationCount(count: Int) {
        dataStore.edit { _dataStore ->
            _dataStore[NOTIFICATION_COUNT] = count
        }
    }

    suspend fun getNotificationCount(): Int? {
        val preferences = dataStore.data.first()
        return if (preferences[NOTIFICATION_COUNT] == null){
            0
        }else {
            preferences[NOTIFICATION_COUNT]
        }
    }

    suspend fun savePendingInvites(pendingInvite: String) {
        dataStore.edit { _dataStore ->
            _dataStore[PENDING_INVITES] = pendingInvite
        }
    }

    suspend fun getPendingInvites() : String? {
        val preferences = dataStore.data.first()
        return preferences[PENDING_INVITES]
    }

    suspend fun saveMaxAccountStatus(maxAccountStatus: String) {
        dataStore.edit { _dataStore ->
            _dataStore[MAX_ACCOUNT] = maxAccountStatus
        }
    }

    suspend fun getMaxAccountStatus(): String? {
        val preferences = dataStore.data.first()
        return preferences[MAX_ACCOUNT]
    }

    private suspend fun saveSourceOfIncome(sourceOfIncome: String) {
        dataStore.edit { _dataStore ->
            _dataStore[SOURCE_OF_INCOME] = sourceOfIncome
        }
    }

    private suspend fun saveFullAddress(address: String) {
        dataStore.edit { _dataStore ->
            _dataStore[FULL_ADDRESS] = address
        }
    }

    private suspend fun saveParish(parish: String) {
        dataStore.edit { _dataStore ->
            _dataStore[PARISH] = parish
        }
    }

    private suspend fun saveTown(town: String) {
        dataStore.edit { _dataStore ->
            _dataStore[TOWN] = town
        }
    }

    private suspend fun saveProfileImg(profileImg: String) {
        dataStore.edit { _dataStore ->
            _dataStore[PROFILE_PIC] = profileImg
        }
    }

    suspend fun savePhoneToDataStore(phoneNumber: String) {
        dataStore.edit { _dataStore ->
            _dataStore[USER_PHONE_NUMBER] = phoneNumber
        }
    }

    suspend fun saveMpinToDataStore(mpin: String) {
        dataStore.edit { _dataStore ->
            _dataStore[MPIN_BIOMETRICS] = mpin
        }
    }

    suspend fun saveIvToDataStore(mpin: String) {
        dataStore.edit { _dataStore ->
            _dataStore[IV_BIOMETRICS] = mpin
        }
    }

    suspend fun getIvFromDataStore(): String? {
        val preferences = dataStore.data.first()
        return preferences[IV_BIOMETRICS]
    }

    suspend fun saveTutorialVersionToDataStore(version: Int) {
        dataStore.edit { _dataStore ->
            _dataStore[SET_TUTORIAL_VERSION] = version
        }
    }


    suspend fun getTutorialVersionToDataStore(): Int? {
        val preferences = dataStore.data.first()
        return preferences[SET_TUTORIAL_VERSION]
    }


    suspend fun getMpinFromDataStore(): String? {
        val preferences = dataStore.data.first()
        return preferences[MPIN_BIOMETRICS]
    }

    suspend fun saveFCMTokenToDataStore(token: String) {
        dataStore.edit { _dataStore ->
            Log.v("Ath1 Token", token)
            _dataStore[FCM_TOKEN] = token
        }
    }

    suspend fun getFCMToken(): String? {
        dataStore.data.collect {
            Log.d("Ath2 Token", it[FCM_TOKEN].toString())
        }
        val preferences = dataStore.data.first()
        return preferences[FCM_TOKEN]
    }

    suspend fun saveAuthTokenToDataStore(authToken: String) {
        dataStore.edit { _dataStore ->
            _dataStore[AUTH_TOKEN] = authToken
        }
    }

    suspend fun getAuthToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[AUTH_TOKEN]
    }

    suspend fun saveAuthTokenTypeToDataStore(authToken: String) {
        dataStore.edit { _dataStore ->
            _dataStore[AUTH_TOKEN_TYPE] = authToken
        }
    }

    suspend fun getAuthTokenType(): String? {
        val preferences = dataStore.data.first()
        return preferences[AUTH_TOKEN_TYPE]
    }

    suspend fun saveTokenExpireTime(authToken: String) {
        dataStore.edit { _dataStore ->
            _dataStore[AUTH_TOKEN_EXPIRE_TIME] = authToken
        }
    }

    suspend fun getTokenExpireTime(): String? {
        val preferences = dataStore.data.first()
        return preferences[AUTH_TOKEN_EXPIRE_TIME]
    }


    suspend fun getUserPhoneNumber(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_PHONE_NUMBER]
    }

    suspend fun saveDeviceNameToDataStore(deviceName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[USER_DEVICE_NAME] = deviceName
        }
    }

    suspend fun getUserProfileImage(): String? {
        val preferences = dataStore.data.first()
        return preferences[PROFILE_IMAGE]
    }

    suspend fun saveProfileImageToDataStore(profileImg: String) {
        dataStore.edit { _dataStore ->
            _dataStore[PROFILE_PIC] = profileImg
        }
    }

    suspend fun saveUserIDToDataStore(deviceName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[USER_ID] = deviceName
        }
    }

    suspend fun getUserID(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_ID]
    }

    suspend fun saveUserBalanceToDataStore(balance: String) {
        dataStore.edit { _dataStore ->
            _dataStore[USER_BALANCE] = balance
        }
    }

    suspend fun getUserBalance(): String? {
        val preferences = dataStore.data.first()
        return preferences[USER_BALANCE]
    }

    suspend fun savePinStatusToDataStore(deviceName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[PIN_STATUS] = deviceName
        }
    }

    suspend fun getPinStatus(): String? {
        val preferences = dataStore.data.first()
        return preferences[PIN_STATUS]
    }

    suspend fun saveAccountStatusToDataStore(deviceName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[ACCOUNT_STATUS] = deviceName
        }
    }

    suspend fun getAccountStatus(): String? {
        val preferences = dataStore.data.first()
        return preferences[ACCOUNT_STATUS]
    }

    suspend fun saveHuaweiStatusToDataStore(statusCode: String) {
        dataStore.edit { _dataStore ->
            _dataStore[HUAWEI_STATUS] = statusCode
        }
    }

    suspend fun getHuaweiStatus(): String? {
        val preferences = dataStore.data.first()
        return preferences[HUAWEI_STATUS]
    }

    suspend fun saveShowTutorialToDataStore(show: String) {
        dataStore.edit { _dataStore ->
            _dataStore[SHOW_TUTORIAL] = show
        }
    }

    suspend fun getShowTutorial(): String? {
        val preferences = dataStore.data.first()
        return preferences[SHOW_TUTORIAL]
    }

    suspend fun saveFullNameToDataStore(fullName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[FULL_NAME] = fullName
        }
    }

    suspend fun getFirstName(): String? {
        val preferences = dataStore.data.first()
        return preferences[FIRST_NAME]
    }

    suspend fun saveFirstNameToDataStore(firstName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[FIRST_NAME] = firstName
        }
    }

    suspend fun getLastName(): String? {
        val preferences = dataStore.data.first()
        return preferences[LAST_NAME]
    }

    suspend fun saveLastNameToDataStore(firstName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[LAST_NAME] = firstName
        }
    }

    suspend fun getDob(): String? {
        val preferences = dataStore.data.first()
        return preferences[DOB]
    }

    suspend fun saveDobToDataStore(firstName: String) {
        dataStore.edit { _dataStore ->
            _dataStore[DOB] = firstName
        }
    }

    suspend fun getFullName(): String? {
        val preferences = dataStore.data.first()
        return preferences[FULL_NAME]
    }

    suspend fun saveNumberVerificationToDataStore(isVerified: Boolean) {
        dataStore.edit { _dataStore ->
            _dataStore[IS_NUMBER_VERIFIED] = isVerified
        }
    }


    suspend fun saveProfileStatusToDataStore(isProfileCreated: Boolean) {
        dataStore.edit { _dataStore ->
            _dataStore[IS_PROFILE_EXISTED] = isProfileCreated
        }
    }


    fun getProfileStatusToDataStore(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[IS_PROFILE_EXISTED]
        }
    }

    suspend fun clearProfileStatus() {
        dataStore.edit { _dataStore ->
            _dataStore.clear()
        }
    }

    fun getPhoneNumberVerificationStatus(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[IS_NUMBER_VERIFIED]
        }
    }

    suspend fun setBiometricsStatusToDataStore(isBiometricsEnabled: Boolean) {
        dataStore.edit { _dataStore ->
            _dataStore[IS_BIOMETRICS_ENABLED] = isBiometricsEnabled
        }
    }

    suspend fun getBiometricsStatus(): Boolean? {
        val preferences = dataStore.data.first()
        return preferences[IS_BIOMETRICS_ENABLED]
//        return dataStore.data.map { preferences ->
//            preferences[IS_BIOMETRICS_ENABLED]
//        }
    }

    suspend fun setUpgradeToMaxStatusToDataStore(isBiometricsEnabled: Boolean) {
        dataStore.edit { _dataStore ->
            _dataStore[IS_UPGRADE_TO_MAX_ALLOWED] = isBiometricsEnabled
        }
    }

    suspend fun getUpgradeToMaxStatusToDataStore(): Boolean? {
        val preferences = dataStore.data.first()
        return preferences[IS_UPGRADE_TO_MAX_ALLOWED]
    }

    suspend fun setUpgradeToMaxInProcessMessage(msg: String) {
        dataStore.edit { _dataStore ->
            _dataStore[UPGRADE_TO_MAX_IN_PROCESS_MSG] = msg
        }
    }

    suspend fun getUpgradeToMaxInProcessMessage(): String? {
        val preferences = dataStore.data.first()
        return preferences[UPGRADE_TO_MAX_IN_PROCESS_MSG]
    }

    suspend fun setUpgradeToMaxPromptMessage(msg: String) {
        dataStore.edit { _dataStore ->
            _dataStore[UPGRADE_TO_MAX_PROMPT_MSG] = msg
        }
    }

    suspend fun getUpgradeToMaxPromptMessage(): String? {
        val preferences = dataStore.data.first()
        return preferences[UPGRADE_TO_MAX_PROMPT_MSG]
    }


    suspend fun saveLastRateUsTimeToDataStore(time: Long) {
        dataStore.edit { _dataStore ->
            _dataStore[RATE_US_TIME] = time
        }
    }

    suspend fun getLastRateUsTime(): Long? {
        val preferences = dataStore.data.first()
        return preferences[RATE_US_TIME]
    }

    suspend fun saveNeverRateUsDataStore(shouldRate: Boolean) {
        dataStore.edit { _dataStore ->
            _dataStore[NEVER_US_TIME] = shouldRate
        }
    }

    suspend fun getNeverRateUsTime(): Boolean {
        val preferences = dataStore.data.first()
        return if (preferences[NEVER_US_TIME] == null) {
            false
        } else preferences[NEVER_US_TIME]!!
    }

    suspend fun saveRateUsDoneOnceDataStore(alreadyRated: Boolean) {
        dataStore.edit { _dataStore ->
            _dataStore[ALREADY_RATED_STATUS] = alreadyRated
        }
    }


    suspend fun getRateUsDoneOnceDataStore(): Boolean {
        val preferences = dataStore.data.first()
        return if (preferences[ALREADY_RATED_STATUS] == null) {
            false
        } else preferences[ALREADY_RATED_STATUS]!!
    }

    suspend fun saveLanguage(token: String) {
        dataStore.edit { _dataStore ->
            _dataStore[LOCAL_LANGUAGE] = token
        }
    }

    suspend fun getLanguage(): String? {
        val preferences = dataStore.data.first()
        return preferences[LOCAL_LANGUAGE]
    }


}