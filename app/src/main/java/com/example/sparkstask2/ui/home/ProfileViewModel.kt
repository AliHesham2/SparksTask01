package com.example.sparkstask2.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sparkstask2.model.ProfileObject

class ProfileViewModel(app: Application, private val userData: ProfileObject?): AndroidViewModel(app) {

    private val _data = MutableLiveData<ProfileObject>()
    val data : LiveData<ProfileObject>
        get() = _data

    init {
        showUserData()
    }

    private fun showUserData(){
        _data.value = userData
    }

}