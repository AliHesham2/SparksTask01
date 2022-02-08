package com.example.sparkstask2.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileObject(val name:String,
                         val email:String,
                         val photoUrl: Uri?): Parcelable
