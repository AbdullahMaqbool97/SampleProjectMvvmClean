package com.example.retrofitsamplewithcleanarchitecture.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.retrofitsamplewithcleanarchitecture.BuildConfig
import com.example.retrofitsamplewithcleanarchitecture.R
import com.example.retrofitsamplewithcleanarchitecture.data.local.AppDatabase
import com.example.retrofitsamplewithcleanarchitecture.data.remote.apiServices.ApiNetworkService
import com.example.retrofitsamplewithcleanarchitecture.data.remote.requests.notification.FCMRequest
import com.example.retrofitsamplewithcleanarchitecture.databinding.BottomsheetAlertDialog2Binding
import com.example.retrofitsamplewithcleanarchitecture.databinding.BottomsheetAlertDialogBinding
import com.example.retrofitsamplewithcleanarchitecture.databinding.BottomsheetAlertDialogDeleteBinding
import com.example.retrofitsamplewithcleanarchitecture.databinding.BottomsheetAlertSuccessAddedDialogBinding
import com.example.retrofitsamplewithcleanarchitecture.interfaces.UserInteractionAwareCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object Utils {

    var bottomSheetDialog: BottomSheetDialog? = null
    var isLogoutAlert: Boolean = false

    @SuppressLint("MissingPermission")
    fun hasInternetConnection(context: Context?): Boolean {
        try {
            if (context == null) return false
            var result = false

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }

            }
            return result
        } catch (e: Exception) {
            return false
        }
    }

    fun getDecimalFormattedString(value: String): String {
        val lst = StringTokenizer(value, ".")
        var str1 = value
        var str2 = ""
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken()
            str2 = lst.nextToken()
        }
        var str3 = ""
        var i = 0
        var j = -1 + str1.length
        if (str1[-1 + str1.length] == '.') {
            j--
            str3 = "."
        }
        var k = j
        while (true) {
            if (k < 0) {
                if (str2.length > 0) str3 = "$str3.$str2"
                return str3
            }
            if (i == 3) {
                str3 = ",$str3"
                i = 0
            }
            str3 = str1[k].toString() + str3
            i++
            k--
        }
    }

    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    @SuppressLint("RestrictedApi")
    fun showSnackBar(
        activity: Activity?,
        containerLayout: ConstraintLayout?,
        context: Context,
        message: String?, gravity: Int
    ) {
        try {
            val snackBar = Snackbar.make(containerLayout!!, "", Snackbar.LENGTH_INDEFINITE)
            val layout = snackBar.view as Snackbar.SnackbarLayout
            val params = layout.layoutParams as FrameLayout.LayoutParams
            params.gravity = gravity
            layout.layoutParams = params
            val snackView: View =
                LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null)
            val textViewTop = snackView.findViewById<View>(R.id.tvHead1) as TextView
            textViewTop.text = message
            layout.setPadding(10, 10, 10, 10)
            layout.setBackgroundColor(context.getColor(R.color.translucent))
            layout.addView(snackView, 0)
            snackBar.duration = 4000
            snackBar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("RestrictedApi")
    fun showSnackBar(
        activity: Activity?,
        containerLayout: CoordinatorLayout?,
        context: Context,
        message: String?, gravity: Int
    ) {
        try {
            val snackBar = Snackbar.make(containerLayout!!, "", Snackbar.LENGTH_LONG)
            val layout = snackBar.view as Snackbar.SnackbarLayout
            val snackView: View =
                LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null)
            val params = layout.layoutParams as CoordinatorLayout.LayoutParams
            params.gravity = Gravity.BOTTOM
            layout.layoutParams = params
            val textViewTop = snackView.findViewById<View>(R.id.tvHead1) as TextView
            textViewTop.text = message
            layout.setPadding(10, 10, 10, 10)
            layout.setBackgroundColor(context.getColor(R.color.black))
            layout.addView(snackView, 0)
            snackBar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("RestrictedApi")
    fun showSnackBarOnViews(
        activity: Activity?,
        context: Context,
        message: String?, gravity: Int
    ) {
        try {
            val snackBar =
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "",
                    Snackbar.LENGTH_LONG
                )
            val layout = snackBar.view as Snackbar.SnackbarLayout
            val snackView: View =
                LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null)
            val params = layout.layoutParams //as CoordinatorLayout.LayoutParams
            //params.gravity = Gravity.BOTTOM
            layout.layoutParams = params
            val textViewTop = snackView.findViewById<View>(R.id.tvHead1) as TextView
            textViewTop.text = message
            layout.setPadding(10, 10, 10, 10)
            layout.setBackgroundColor(context.getColor(R.color.translucent))
            layout.addView(snackView, 0)
            snackBar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("RestrictedApi")
    fun showSnackBarOnBottomSheet(
        dialog: View,
        context: Context,
        message: String?,
    ) {
        try {
            val snackBar =
                Snackbar.make(dialog, "", Snackbar.LENGTH_LONG)
            val layout = snackBar.view as Snackbar.SnackbarLayout
            val snackView: View =
                LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null)
            val params = layout.layoutParams //as CoordinatorLayout.LayoutParams
            //params.gravity = Gravity.BOTTOM
            layout.layoutParams = params
            val textViewTop = snackView.findViewById<View>(R.id.tvHead1) as TextView
            textViewTop.text = message
            layout.setPadding(10, 10, 10, 10)
            layout.setBackgroundColor(context.getColor(R.color.translucent))
            layout.addView(snackView, 0)
            snackBar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun hideSoftKeyboard(activity: Activity, view: View) {
        val inputManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager/*
         * If no view is focused, an NPE will be thrown
         */
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showSoftKeyboard(activity: Activity, view: View) {
        // val view = activity.currentFocus
        val methodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            assert(view != null)
            methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            Timber.e("Issue while opening softKeyboard $e")
        }
    }

    /**
     * This method checks if the device can support biometric authentication APIs
     */
    fun checkBiometricIsAvailable(context: Context): Boolean {
        var available = false
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_WEAK or BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                available = true
                Timber.i("App can authenticate using biometrics.")
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                available = false
                Timber.i("No biometric features available on this device.")
            }

//            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
//                available = false
//                Timber.i("Biometric features are currently unavailable.")
//            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                available = false
                Timber.i("The user hasn't associated any biometric credentials with their account.")
            }

        }
        return available
    }

    fun promptBioMetricDialog(fragment: Fragment, context: Context): Boolean {
        var isAuthenticated: Boolean = false
        val executor: Executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt =
            BiometricPrompt(fragment, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                    isAuthenticated = false
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    isAuthenticated = true
                    Toast.makeText(context, "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                    isAuthenticated = false
                }
            })

        val promptInfo: BiometricPrompt.PromptInfo =
            BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Verification")
                .setSubtitle("Touch the fingerprint sensor").setNegativeButtonText("USE PIN")
                .build()

        biometricPrompt.authenticate(promptInfo)
        return isAuthenticated
    }

    fun getAndroidDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalizeWords(model)
        } else {
            capitalizeWords(manufacturer) + " " + model
        }
    }

    @SuppressLint("HardwareIds")
    fun getAndroidDeviceID(context: Context): String {
        val uniqueAndroidId =
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return uniqueAndroidId
    }

    private fun capitalizeWords(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    fun showAlertBottomSheetDialog(
        context: Context,
        activity: Activity,
        iconId: Int,
        heading: String,
        subheading: String,
        subheadingText: String,
        description: String,
        positiveButtonText: String,
        negativeButtonText: String? = null,
        cancelable: Boolean? = true,
        crossButton: Boolean? = false,
        isLogout: Boolean? = false,
        customColor: Boolean = false,
        positiveClicked: () -> Unit?,
        negativeClicked: () -> Unit?,
    ) {
        try {
            if ((bottomSheetDialog == null || isLogout == isLogoutAlert || bottomSheetDialog?.isShowing == false) && !activity.isFinishing) {
                isLogoutAlert = if (isLogout == true) false else isLogout!!
                bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
                bottomSheetDialog!!.setCancelable(cancelable!!)
                val bindingSheet =
                    DataBindingUtil.inflate<BottomsheetAlertDialogBinding>(
                        activity.layoutInflater,
                        R.layout.bottomsheet_alert_dialog,
                        null,
                        false
                    )
                bottomSheetDialog!!.setContentView(bindingSheet.root)
                activity.let {
                    bottomSheetDialog!!.window?.setCallback(
                        UserInteractionAwareCallback(
                            bottomSheetDialog!!.window?.callback,
                            it
                        )
                    )
                }
                bottomSheetDialog!!.window!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        android.R.color.transparent
                    )
                )
                bindingSheet.root.background =
                    ContextCompat.getDrawable(context, R.drawable.top_rounded_white_card)
                val behavior by lazy { bottomSheetDialog!!.behavior }
                behavior.isDraggable = true
                behavior.isHideable = true
                bindingSheet.imageView.setImageResource(iconId)
                bindingSheet.tvHeading.text = heading
                if (customColor) {
                    bindingSheet.tvHeading.setTextColor(Color.parseColor("#00BDFE"))
                }
                if (subheading.isNotEmpty() || subheadingText.isNotEmpty()) {
                    bindingSheet.tvSubheading.visibility = View.VISIBLE
                    bindingSheet.tvSubheadingDesc.visibility = View.VISIBLE

                    bindingSheet.tvSubheading.text = subheading
                    bindingSheet.tvSubheadingDesc.text = subheadingText
                }
                bindingSheet.tvDescription.text = description
                bindingSheet.btnConfirm.text = positiveButtonText
                crossButton?.let {
                    bindingSheet.ivHandle.visibility = if (it)
                        View.VISIBLE
                    else
                        View.GONE
                }
                bindingSheet.ivHandle.setOnClickListener {
                    bottomSheetDialog!!.dismiss()
                }
                if (negativeButtonText.isNullOrEmpty())
                    bindingSheet.tvCancel.visibility = View.GONE
                else
                    bindingSheet.tvCancel.text = negativeButtonText

                bindingSheet.btnConfirm.setOnClickListener {
                    positiveClicked.invoke()
                    bottomSheetDialog!!.dismiss()
                }
                bindingSheet.tvCancel.setOnClickListener {
                    negativeClicked.invoke()
                    bottomSheetDialog!!.dismiss()
                }
                bottomSheetDialog!!.show()
            }
        } catch (e: Exception) {
            Log.e("zaza", e.localizedMessage)
        }
    }

    fun showAlertBottomSheetSuccessDialog(
        context: Context,
        activity: Activity,
        iconId: Int,
        heading: String,
        subheading: String,
        subheadingText: String,
        description: String,
        positiveButtonText: String,
        negativeButtonText: String? = null,
        cancelable: Boolean? = true,
        crossButton: Boolean? = false,
        isLogout: Boolean? = false,
        positiveClicked: () -> Unit?,
        negativeClicked: () -> Unit?,
    ) {
        try {
            if ((bottomSheetDialog == null || isLogout == isLogoutAlert || bottomSheetDialog?.isShowing == false) && !activity.isFinishing) {
                isLogoutAlert = if (isLogout == true) false else isLogout!!
                bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
                bottomSheetDialog!!.setCancelable(cancelable!!)
                val bindingSheet =
                    DataBindingUtil.inflate<BottomsheetAlertSuccessAddedDialogBinding>(
                        activity.layoutInflater,
                        R.layout.bottomsheet_alert_success_added_dialog,
                        null,
                        false
                    )
                bottomSheetDialog!!.setContentView(bindingSheet.root)
                activity.let {
                    bottomSheetDialog!!.window?.setCallback(
                        UserInteractionAwareCallback(
                            bottomSheetDialog!!.window?.callback,
                            it
                        )
                    )
                }
                bottomSheetDialog!!.window!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        android.R.color.transparent
                    )
                )
                bindingSheet.root.background =
                    ContextCompat.getDrawable(context, R.drawable.top_rounded_white_card)
                val behavior by lazy { bottomSheetDialog!!.behavior }
                behavior.isDraggable = true
                behavior.isHideable = true
                bindingSheet.imageView.setImageResource(iconId)
                bindingSheet.tvHeading.text = heading
                if (subheading.isNotEmpty() || subheadingText.isNotEmpty()) {
                    bindingSheet.tvSubheading.visibility = View.VISIBLE
                    bindingSheet.tvSubheadingDesc.visibility = View.VISIBLE

                    bindingSheet.tvSubheading.text = subheading
                    bindingSheet.tvSubheadingDesc.text = subheadingText
                }
                bindingSheet.tvDescription.text = description
                bindingSheet.btnConfirm.text = positiveButtonText
                bindingSheet.btnHome.text = negativeButtonText

                crossButton?.let {
                    bindingSheet.ivHandle.visibility = if (it)
                        View.VISIBLE
                    else
                        View.GONE
                }
                bindingSheet.ivHandle.setOnClickListener {
                    bottomSheetDialog!!.dismiss()
                }
//                if (negativeButtonText.isNullOrEmpty())
//                    bindingSheet.tvCancel.visibility = View.GONE
//                else
//                    bindingSheet.tvCancel.text = negativeButtonText

                bindingSheet.btnConfirm.setOnClickListener {
                    positiveClicked.invoke()
                    bottomSheetDialog!!.dismiss()
                }
                bindingSheet.btnHome.setOnClickListener {
                    negativeClicked.invoke()
                    bottomSheetDialog!!.dismiss()
                }
                bottomSheetDialog!!.show()
            }
        } catch (e: Exception) {
            Log.e("zaza", e.localizedMessage)
        }
    }

    /*Some changes due to force upgrade dailog*/
    fun showAlertBottomSheetDeleteDialog(
        context: Context,
        activity: Activity,
        iconId: Int,
        heading: String,
        subheading: String,
        subheadingText: String,
        description: String,
        positiveButtonText: String,
        negativeButtonText: String? = null,
        cancelable: Boolean? = true,
        crossButton: Boolean? = false,
        isLogout: Boolean? = false,
        isDraggable: Boolean? = true,
        positiveClicked: () -> Unit?,
        negativeClicked: () -> Unit?,
    ) {
        try {
            if ((bottomSheetDialog == null || isLogout == isLogoutAlert || bottomSheetDialog?.isShowing == false) && !activity.isFinishing) {
                isLogoutAlert = if (isLogout == true) false else isLogout!!
                bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
                bottomSheetDialog!!.setCancelable(cancelable!!)
                val bindingSheet = DataBindingUtil.inflate<BottomsheetAlertDialogDeleteBinding>(
                    activity.layoutInflater,
                    R.layout.bottomsheet_alert_dialog_delete,
                    null,
                    false
                )
                bottomSheetDialog!!.setContentView(bindingSheet.root)
                activity.let {
                    bottomSheetDialog!!.window?.setCallback(
                        UserInteractionAwareCallback(
                            bottomSheetDialog!!.window?.callback,
                            it
                        )
                    )
                }
                bottomSheetDialog!!.window!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        android.R.color.transparent
                    )
                )
                bindingSheet.root.background =
                    ContextCompat.getDrawable(context, R.drawable.top_rounded_white_card)
                val behavior by lazy { bottomSheetDialog!!.behavior }
                behavior.isDraggable = isDraggable!!
                behavior.isHideable = true
                bindingSheet.imageView.setImageResource(iconId)
                bindingSheet.tvHeading.text = heading
                if (subheading.isNotEmpty() || subheadingText.isNotEmpty()) {
                    bindingSheet.tvSubheading.visibility = View.VISIBLE
                    bindingSheet.tvSubheadingDesc.visibility = View.VISIBLE

                    bindingSheet.tvSubheading.text = subheading
                    bindingSheet.tvSubheadingDesc.text = subheadingText
                }
                bindingSheet.tvDescription.text = description
                bindingSheet.btnConfirm.text = positiveButtonText
                crossButton?.let {
                    bindingSheet.ivHandle.visibility = if (it)
                        View.VISIBLE
                    else
                        View.GONE
                }
                bindingSheet.ivHandle.setOnClickListener {
                    bottomSheetDialog!!.dismiss()
                }
                if (negativeButtonText.isNullOrEmpty())
                    bindingSheet.tvCancel.visibility = View.GONE
                else
                    bindingSheet.tvCancel.text = negativeButtonText

                bindingSheet.btnConfirm.setOnClickListener {
                    positiveClicked.invoke()
                    if (isDraggable)
                        bottomSheetDialog!!.dismiss()
                }
                bindingSheet.tvCancel.setOnClickListener {
                    negativeClicked.invoke()
                    bottomSheetDialog!!.dismiss()
                }
                bottomSheetDialog!!.show()
            }
        } catch (e: Exception) {
            Log.e("zaza", e.localizedMessage)
        }
    }

    fun showAlertBottomSheetDialog(
        context: Context,
        activity: Activity,
        iconId: Int,
        heading: String,
        description1: String?,
        description2: String?,
        description3: String?,
        positiveButtonText: String,
        negativeButtonText: String? = null,
        topButtonText: String? = null,
        cancelable: Boolean? = true,
        crossButton: Boolean? = false,
        isLogout: Boolean? = false,
        isDraggable: Boolean? = true,
        positiveClicked: () -> Unit?,
        negativeClicked: () -> Unit?,
        topButtonClicked: () -> Unit?,
    ) {
        try {
            if ((bottomSheetDialog == null || isLogout == isLogoutAlert || bottomSheetDialog?.isShowing == false) && !activity.isFinishing) {
                isLogoutAlert = if (isLogout == true) false else isLogout!!
                bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
                bottomSheetDialog!!.setCancelable(cancelable!!)
                val bindingSheet = DataBindingUtil.inflate<BottomsheetAlertDialog2Binding>(
                    activity.layoutInflater,
                    R.layout.bottomsheet_alert_dialog2,
                    null,
                    false
                )
                bottomSheetDialog!!.setContentView(bindingSheet.root)
                activity.let {
                    bottomSheetDialog!!.window?.setCallback(
                        UserInteractionAwareCallback(
                            bottomSheetDialog!!.window?.callback,
                            it
                        )
                    )
                }
                bottomSheetDialog!!.window!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        android.R.color.transparent
                    )
                )
                bindingSheet.root.background =
                    ContextCompat.getDrawable(context, R.drawable.top_rounded_white_card)
                val behavior by lazy { bottomSheetDialog!!.behavior }
                behavior.isDraggable = isDraggable!!
                behavior.isHideable = true
                bindingSheet.imageView.setImageResource(iconId)
                bindingSheet.tvHeading.text = heading


                bindingSheet.tvDescription1.text = description1
                bindingSheet.tvDescription2.text = description2
//                bindingSheet.tvDescription3.text = description3

                bindingSheet.btnUpgrade.text = positiveButtonText
                bindingSheet.tvHome.text = negativeButtonText
                bindingSheet.eligibleLocationBtn.text = topButtonText
                crossButton?.let {
                    bindingSheet.ivHandle.visibility = if (it)
                        View.VISIBLE
                    else
                        View.GONE
                }
                bindingSheet.ivHandle.setOnClickListener {
                    bottomSheetDialog!!.dismiss()
                }
                if (positiveButtonText.isNullOrEmpty())
                    bindingSheet.btnUpgrade.visibility = View.GONE
                else if (positiveButtonText == "upgrading") {
                    bindingSheet.btnUpgrade.isEnabled = false
                    bindingSheet.btnUpgrade.isClickable = false
                    bindingSheet.btnUpgrade.background =
                        (ContextCompat.getDrawable(context, R.drawable.button_gradient_inactive))
                    bindingSheet.btnUpgrade.text = "upgrade_for_next_time"
                }

                bindingSheet.btnUpgrade.setOnClickListener {
                    bottomSheetDialog!!.dismiss()
                    positiveClicked.invoke()
                    if (isDraggable)
                        bottomSheetDialog!!.dismiss()
                }
                bindingSheet.tvHome.setOnClickListener {
                    negativeClicked.invoke()
                    bottomSheetDialog!!.dismiss()
                }
                bindingSheet.eligibleLocationBtn.setOnClickListener {
                    topButtonClicked.invoke()
                    bottomSheetDialog!!.dismiss()
                }
                bottomSheetDialog?.setOnShowListener { dialog ->
                    bottomSheetDialog = dialog as BottomSheetDialog
                    val bottomSheet: FrameLayout =
                        bottomSheetDialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
                    BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                bottomSheetDialog!!.show()
            }
        } catch (e: Exception) {
            Log.e("zaza", e.localizedMessage)
        }
    }

    fun NavController.safeNavigate(direction: NavDirections) {
        Timber.d("NAVIGATION :: Click happened")
        currentDestination?.getAction(direction.actionId)?.run {
            Timber.d("NAVIGATION :: Click Propagated")
            navigate(direction)
        }
    }

    fun NavController.safeNavigate(actionId: Int, bundle: Bundle? = null) {
        Timber.d("NAVIGATION :: Click happened")
        currentDestination?.getAction(actionId)?.run {
            Timber.d("NAVIGATION :: Click Propagated")
            bundle?.let {
                navigate(actionId, bundle)
            } ?: navigate(actionId)
        }
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    fun setProfileView(
        context: Context?,
        tVUserName: TextView,
        profile_image: ImageView,
        profileUserName: String?,
        profileImgUrl: String?,
        letters: Int
    ) {
        if (profileUserName != null) {
            var name = ""
            var picture = ""
            var profileLetter = ""
            name = profileUserName //Local otherwise getCloudName
            if (name == null || name.isEmpty() || name == "Unknown") {
                name = profileUserName
            }
            if (profileImgUrl?.isNotEmpty() == true && profileImgUrl != "") {
                picture = profileImgUrl
                profile_image.visibility = View.VISIBLE
                tVUserName.visibility = View.GONE
                Glide.with(context!!).load(picture)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profile_image)
            } else {
                if (name != null && name != "") {
                    if (letters == 1) {
                        profileLetter =
                            name.uppercase(Locale.ROOT)[0]
                                .toString() + ""
                    } else {
                        /* if (getNoOfWords(name) == 1) {
                        profileLetter =
                            name.uppercase()[0]
                                .toString() + ""
                    } else {*/
                        val separated: Array<String> =
                            name.split(" ").toTypedArray()
                        if (separated.size > 1) { //profile_letter = separated[0].toUpperCase().charAt(0) + "" + separated[1].toUpperCase().charAt(0);
                            profileLetter = ""
                            for (s in separated) if (s != "") profileLetter += s.uppercase()[0]
                        } else if (separated.size == 1) {
                            profileLetter =
                                separated[0].uppercase()[0].toString() + ""
                        } else {
                            profileLetter = ""
                        }
                        // }
                    }
                } else profileLetter = ""
                tVUserName.visibility = View.VISIBLE
                profile_image.visibility = View.GONE
                if (profileLetter.length > 2) {
                    tVUserName.text = profileLetter.substring(0, 2)
                } else {
                    tVUserName.text = profileLetter
                }
            }
        }
    }

    private fun getNoOfWords(str: String): Int {
        val words = str.trim { it <= ' ' }
        return if (words.isEmpty()) 0 else words.split("\\s+").toTypedArray().size
    }

    fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
        val divider = DividerItemDecoration(
            this.context,
            DividerItemDecoration.VERTICAL
        )
        val drawable = ContextCompat.getDrawable(
            this.context,
            drawableRes
        )
        drawable?.let {
            divider.setDrawable(it)
            addItemDecoration(divider)
        }
    }

    fun getCardExpiryDate(monthIndex: Int, year: Int): String {
        var monthStr = ""
        var yearStr = ""
        var month = monthIndex
        month++
        monthStr = if (month < 10) {
            "0$month"
        } else {
            month.toString()
        }
        yearStr = year.toString().substring(2)
        return "$monthStr/$yearStr"
    }

    fun keepNumbersOnlyInEditText(s: CharSequence): String {
        return s.toString()
            .replace("[^0-9]".toRegex(), "") // Should of course be more robust
    }

    fun formatNumbersAsTRN(s: CharSequence): String {
        var groupDigits = 0
        var tmp = ""
        for (element in s) {  //i in 0 until s.length
            tmp += element
            ++groupDigits
            if (groupDigits == 3) {
                tmp += "-"
                groupDigits = 0
            }
        }
        return tmp
    }

    fun copyText(
        context: Context,
        label: String, value: String
    ) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText(label, value)
        clipboard.setPrimaryClip(clip)
    }

    fun EditText.stickPrefix(prefix: String) {
        this.addTextChangedListener(afterTextChanged = {
            if (!it.toString().startsWith(prefix) && it?.isNotEmpty() == true) {
                this.setText(prefix + this.text)
                this.setSelection(this.length())
            } else if (this.text.toString() == prefix) {
                this.setText("")
            }
        })
    }

    fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val trustManagerFactory: TrustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> =
                trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                "Unexpected default trust managers:" + trustManagers.contentToString()
            }

            val trustManager =
                trustManagers[0] as X509TrustManager


            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            builder.addInterceptor(loggingInterceptor)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun displayCustomLoaderView(activity: Activity?, loading: Boolean): Int {
        var showLoader = 0
        when (loading) {
            true -> {
                val window = activity!!.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = activity.resources.getColor(R.color.colorDim)
                showLoader = View.VISIBLE
            }
            false -> {
                val window = activity!!.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = activity.resources.getColor(R.color.colorOnPrimary)
                showLoader = View.GONE
            }
        }
        return showLoader
    }

    fun String.removeDashes(): String {
        return this.replace("-", "")
    }

    fun sendToken(id: String, token: String) {
        val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val request: Request = chain.request().newBuilder().build()
            chain.proceed(request)
        })

        val fcmRequest = FCMRequest(userId = id, token = token)

        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_URL)
                .client(
                    httpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES)
                        .build()
                ).build()
        val service = retrofit.create(ApiNetworkService::class.java)

        val call: Call<JsonObject> = service.sentFcmToken(fcmRequest)

        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    Log.e("zaza", "FCM Token Sent")

                } else {
                    response.errorBody().toString()
                    Log.e("zaza", "Issue while sending FCM Token")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.localizedMessage
                Log.e("zaza", "FCM Token Sent Failed")
            }
        })
    }

    fun getDifference(date1: Date, date2: Date) {
        val diff: Long = date1.time - date2.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
    }

    /* fun identifyUserSwrve(userID: String?) {
         SwrveSDK.identify(userID, object : SwrveIdentityResponse {
             override fun onSuccess(status: String, swrveId: String) {
                 // Success, continue with your logic
                 Log.d("SendSwrveData", "sucess")

             }

             override fun onError(responseCode: Int, errorMessage: String) {
                 // Error should be handled.
                 Log.d("SendSwrveData", "fail")

             }
         })
     }*/

    fun sendUserProperties(
        firstName: String,
        lastName: String,
        dob: String,
        parish: String,
        town: String,
        streetAddress: String,
        phoneNumber: String,
        country: String,
        emailAddress: String,
        trnNumber: String,
        sourceOfIncome: String,
        walletType: String,
        pendingInvite: String,
        submittedComplaints: String,
        balance: String,
        bioMetricEnabled: Boolean
    ) {
        val walletTypeValue: String = when (walletType) {
            "IN_PROGRESS" -> {
                "basic"

            }
            "ENABLED" -> {
                "pro"

            }
            else -> {
                "basic"

            }
        }
        /*val attributes: MutableMap<String, String> = HashMap()
        attributes["first_name"] = firstName
        attributes["last_name"] = lastName
        attributes["DOB"] = dob
        attributes["Parish"] = parish
        attributes["Town"] = town
        attributes["street_address"] = streetAddress
        attributes["phone_number"] = phoneNumber
        attributes["country"] = country
        attributes["biometrics enabled"] = "$bioMetricEnabled"
        attributes["email_address"] = emailAddress
        attributes["TRN"] = trnNumber
        attributes["source_of_income"] = sourceOfIncome
        attributes["wallet_type"] = walletTypeValue
        attributes["pending_invites"] = pendingInvite
        attributes["submitted_complaints"] = submittedComplaints
        attributes["balance"] = balance
        SwrveSDK.userUpdate(attributes)*/

    }

    /*fun getMultiLingualString(module: String, key: String, multiLingual: AppDatabase): String {

        try {
            multiLingual.multiLingualDao().getByModule(module)?.json.let {
                if (it != null) {
                    return JSONObject(it).getString(key)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }*/
}