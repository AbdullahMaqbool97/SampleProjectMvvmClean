package com.example.retrofitsamplewithcleanarchitecture.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import com.example.retrofitsamplewithcleanarchitecture.data.local.AppDatabase
import com.example.retrofitsamplewithcleanarchitecture.presentation.ui.BaseActivity
import com.example.retrofitsamplewithcleanarchitecture.util.Utils.showSnackBarOnViews
import javax.inject.Inject


abstract class BaseFragment : Fragment() {
    private var baseViewModel: BaseViewModel? = null
    var mBaseActivity: BaseActivity? = null

//    @Inject
//    lateinit var preferenceStorage: OnBoardingPrefsManager

    @Inject
    lateinit var multiLingual: AppDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mBaseActivity = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel = getViewModel()
        baseViewModel?.outcomeLiveData?.observe(viewLifecycleOwner) {
            when (it) {
                is Outcome.Start -> {
                    (activity as BaseActivity).displayProgressBar(true)
                }
                is Outcome.End -> {
                    (activity as BaseActivity).displayProgressBar(false)
                }
                is Outcome.Success -> {
                    (activity as BaseActivity).displayProgressBar(false)
                }
                is Outcome.Failure -> {
                    (activity as BaseActivity).displayProgressBar(false)

                    val currentString = baseViewModel!!.outcomeLiveData.value.toString()
                    val separated = currentString.split("zaza").toTypedArray()

                    if (separated[1] == "424") {
                        /*val headingText = resources.getText(R.string.text_error).toString()
                        val descriptionText = separated[0]
                        val buttonText = resources.getText(R.string.ok).toString()
                        Utils.showAlertBottomSheetDialog(
                            requireContext(),
                            requireActivity(),
                            R.drawable.ic_alert,
                            headingText,
                            "", "",
                            descriptionText,
                            buttonText,
                            cancelable = false,
                            isLogout = true,
                            positiveClicked =
                            {
                                lifecycleScope.launch {
                                    preferenceStorage.saveProfileStatusToDataStore(false)
                                }
                                val intent=Intent(requireContext(),SignUpActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            }){}*/

                    } else {
                        showSnackBarOnViews(
                            requireActivity(),
                            requireContext(),
                            separated[0],
                            Gravity.BOTTOM
                        )
                    }
                }
                is Outcome.NetworkError -> {
                    (activity as BaseActivity).displayProgressBar(false)
                    showSnackBarOnViews(
                        requireActivity(),
                        requireContext(),
                        baseViewModel!!.outcomeLiveData.value.toString(),
                        Gravity.TOP
                    )
                }
                else -> {}
            }
        }
    }

    //    fun preferenceStorage(): OnBoardingPrefsManager = mBaseActivity!!.preferenceStorage
    abstract fun getViewModel(): BaseViewModel?
    abstract fun setLanguage()

}