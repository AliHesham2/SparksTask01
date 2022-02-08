package com.example.sparkstask2.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sparkstask2.model.ProfileObject

class ViewModelFactory(private val application: Application,private val data: ProfileObject?): ViewModelProvider.Factory {
    @Suppress(("unchecked_cast"))
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(application,data) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}