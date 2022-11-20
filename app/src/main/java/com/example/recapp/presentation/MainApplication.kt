package com.example.recapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    //Should be stored in a datastore or cache
    val isDark= mutableStateOf(false)

    fun toggleLightTheme(){
        isDark.value=!isDark.value
    }

}