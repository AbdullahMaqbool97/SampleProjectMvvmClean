package com.example.retrofitsamplewithcleanarchitecture.util

import androidx.lifecycle.MutableLiveData
import com.example.retrofitsamplewithcleanarchitecture.BuildConfig
import org.json.JSONObject
import java.util.regex.Pattern

object Constants {

    const val DATABASE_NAME = "app_db"
    const val BASE_URL_AUTH = BuildConfig.API_URL

    const val AUTO_AUTH_PREFS: String = "AUTO_AUTH_PREFS"
    const val HELP_AND_SUPPORT: String = "HELP_AND_SUPPORT"
    const val PRIVACY_POLICY: String = "PRIVACY_POLICY"
    const val TERMS_AND_CONDITIONS: String = "TERMS_AND_CONDITIONS"
    const val USER_PHONE_NUMBER_PREF: String = "USER_PHONE_NUMBER"
    const val USER_CURRENCY: String = "USER_CURRENCY"
    const val AUTH_TOKEN_PREF: String = "AUTH_TOKEN"
    const val PROFILE_IMAGE_PREF: String = "PROFILE_IMAGE"
    const val FCM_TOKEN_PREF: String = "FCM_TOKEN"
    const val AUTH_TOKEN_TYPE_PREF: String = "AUTH_TOKEN_TYPE"
    const val AUTH_TOKEN_TIME_PREF: String = "3600"
    const val LAST_RATE_US_TIME: String = "LAST_RATE_US_TIME"
    const val NEVER_RATE_US: String = "NEVER_RATE_US"
    const val ALREADY_RATED: String = "ALREADY_RATED"

    const val PROFILE_IMG: String = "PROFILE_IMG"
    const val TRN: String = "TRN_NUMBER"
    const val EMAIL_ADDRESS: String = "EMAIL_ADDRESS"
    const val INCOME_SOURCE: String = "INCOME_SOURCE"
    const val ADDRESS_FULL: String = "ADDRESS_FULL"
    const val PREF_PENDING_INVITES: String = "PREF_PENDING_INVITES"
    const val PARISH_: String = "PARISH_"
    const val TOWN_: String = "TOWN_"
    const val IS_MAX_ACCOUNT: String = "IS_MAX_ACCOUNT"
    const val TUTORIAL_VERSION: String = "TUTORIAL_VERSION"
    const val UPDATED_TUTORIAL_VERSION: String = "UPDATED_TUTORIAL_VERSION"
    const val COUNT_NOTIFICATION: String = "COUNT_NOTIFICATION"
    const val IS_UPGRADE_MAX_ALLOWED: String = "IS_UPGRADE_MAX_ALLOWED_"
    const val UPGRADE_MAX_MESSAGE: String = "MESSAGE_UPGRADE_MAX"
    const val UPGRADE_MAX_PROMPT_MESSAGE: String = "MESSAGE_PROMPT_UPGRADE_MAX"
    const val APIMTOKEN: String = "APIM_TOKEN"

    const val SUCESS_CODE: String = "200"
    const val USER_DEVICE_NAME_PREF: String = "USER_DEVICE_NAME"
    const val USER_ID_PREF: String = "USER_ID"
    const val USER_BALANCE_PREF: String = "USER_BALANCE"
    const val ACCOUNT_STATUS_PREF: String = "ACCOUNT_STATUS"
    const val PIN_STATUS_PREF: String = "PIN_STATUS"
    const val PIN_STATUS_PENDING: String = "PIN_STATUS_PENDING"
    const val HUAWEI_STATUS_PREF: String = "HUAWEI_STATUS"
    const val FULL_NAME_PREF: String = "FULL_NAME"
    const val FIRST_NAME_PREF: String = "FIRST_NAME"
    const val LAST_NAME_PREF: String = "LAST_NAME"
    const val DOB_PREF: String = "DOB"
    const val SHOW_TUTORIAL_PREF: String = "SHOW_TUTORIAL"
    const val USER_PROFILE_EXISTED: String = "USER_PROFILE_EXISTED"
    const val BIOMETRICS_ENABLED: String = "IS_BIOMETRICS_ENABLED"
    const val MPIN_BIOMETRIC: String = "MPIN_BIOMETRICS"
    const val IV_BIO: String = "IV_BIO"
    var MASTERCARD_STRING: String = "track_card_layout"
    const val NUMBER_VERIFIED: String = "NUMBER_VERIFIED"
    const val NIGHT_MODE = "NIGHT_MODE"
    const val SYNC_DATA_WORK_NAME = "sync_data_work_name"
    const val API_SESSION_MANAGER = "API_SESSION_MANAGER"
    const val LANGUAGE: String = "LOCAL_LANGUAGE"

    var TYPE = ""
    var DEEPLINKURL = ""
    var REQUESTID = ""
    var DEEPLINKCOUNT = 0
    var jsonObject: JSONObject = JSONObject()
    var billDetailsMessage = ""
    var shouldReloadWallet = MutableLiveData(true)
    var isLoggedIn = false
    var DEEPLINKEXTRADATA = ""

    var internalErrorText = "Internal Server Error"
    var badGatewayText = "Bad Gateway"
    var gatewayTimoutText = "504 Gateway Time-out"
    var noInternetText = "No internet connected"
    var mycashServerErrorText =
        "We are very sorry. we cannot connect to the MyCash server to fetch your account status. Please try again later!"


    var NO_CONNECTION_ERROR_MESSAGE = "No connection!"
    var TIME_OUT_ERROR_MESSAGE = "Time out! , Cannot connect to Server. Please try again shortly"
    var EMPTY_RESPONSE_ERROR_MESSAGE = "Something went wrong!"
    var NOT_DEFINED_ERROR_MESSAGE = "Something went wrong!"
    var UNAVAILABLE_ERROR_MESSAGE = "Requested Resource not available!"
    var UNAUTHORIZED_ERROR_MESSAGE = "Unauthorized!"
    var NULL_POINTER_EXCEPTION_MESSAGE = "Something went wrong!"
    var INTERNAL_SERVER_ERROR_MESSAGE = "Cannot connect to Server. Please try again shortly"
    var BAD_GATEWAY_MESSAGE = "Bad Gateway Error. Please try again shortly"

//    var isAccessibleByMiniWallet : ArrayList<DataItemsIsAccessibleByMiniWallet>? = null


    // Other keys
    const val DELAY_TIME_MILLIS: Long = 3000

    //rate us delay
    const val RATE_US_TIME_DELAY: Long = 604800000 //1 week
    const val CLIENT_TOKEN =
        "Basic UmREd2NRUmtHYTM0cnJzTWNHcE9OcEpJbE9NYTp3YzRqRGZBcUFZeXZWOXdoVkU0cTRGOFVmMFlh"
    val CODE_PATTERN: Pattern =
        Pattern.compile("([0-9]{0,3})|([0-9]{3}-)+|([0-9]{3}-[0-9]{0,3})+")
    val CARD_PATTERN: Pattern =
        Pattern.compile("([0-9]{0,4})|([0-9]{4} )+|([0-9]{4} [0-9]{0,4})+")

//    var logOutReference: BaseActivity.SessionLogout.logOut? = null

    object AccountConstants {
        const val ACCOUNT_LIMIT_AND_SETTINGS = "Account Limits & Settings"
        const val TRANSACTION_HISTORY = "Transaction History"
        const val LINKED_BANK_ACCOUNTS_CARDS = "Linked Bank Accounts & Cards"
        const val RECURRING_PAYMENTS = "Recurring Payments"
        const val INVITE_EARN = "Referral"
        const val BECOME_MERCHANT = "Become a MyCash Merchant"
        const val BECOME_MASTERCARD = "MyCash Prepaid MasterCard"
        const val REDEEM_VOUCHER = "Redeem Voucher"
    }

    object HelpNSupportConstants {
        const val FAQ = "Frequently Asked Questions (FAQs)"
        const val CHAT_WITH_SUPPORT_AGENT = "Chat with a Support Agent"
        const val SUBMIT_COMPLAINT = "Submit a Complaint"
        const val VIEW_COMPLAINTS = "View Submitted Complaints"
        const val PRIVACY_POLICY = "Privacy Policy"
        const val TERMS_N_CONDITIONS = "Terms & Conditions"
    }

    object IncomeSource {
        const val SALARY = "Salary"
        const val WORKING_PROFESSIONAL = "Working Professional"
        const val PARENTS = "Parents"
    }

    enum class EditType {
        EMAIL,
        ADDRESS,
        INCOME_SOURCE,
        PIC
    }

    object MaxContactSelection {
        const val count = 20
    }

    object Authenticator {
        var count = 0
    }

    enum class Frequency {
        DAILY,
        WEEKLY,
        MONTHLY
    }
}