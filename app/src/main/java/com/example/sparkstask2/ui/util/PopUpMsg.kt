package com.example.sparkstask2.ui.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import com.example.sparkstask2.R

class PopUpMsg {
    companion object{
        private lateinit var  mProgressDialog : Dialog

        fun toastMsg(context: Context, msg:String){
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
        }
        fun showDialogue(context: Context){
            mProgressDialog = Dialog(context)
            mProgressDialog.setContentView(R.layout.loading)
            mProgressDialog.setCancelable(false)
            mProgressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mProgressDialog.show()
        }
        fun hideDialogue(){
            mProgressDialog.dismiss()
        }
    }
}