package com.example.retrofitsamplewithcleanarchitecture.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

//    @Inject
//    lateinit var sessionManager: SessionManager

    abstract fun displayProgressBar(loading: Boolean)

}