package com.example.sparkstask2.ui.util

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.sparkstask2.R

@BindingAdapter("userPicture")
fun userPicture(img: ImageView, pic: Uri?){
    if (pic != null ){
        val imgUri = pic.buildUpon().scheme("https").build()
        Glide
            .with(img.context)
            .load(imgUri)
            .circleCrop()
            .error(R.drawable.df)
            .into(img)
    }else{
        Glide
            .with(img.context)
            .load(R.drawable.df)
            .circleCrop()
            .into(img)
    }
}