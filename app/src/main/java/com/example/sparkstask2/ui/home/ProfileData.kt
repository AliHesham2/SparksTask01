package com.example.sparkstask2.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sparkstask2.R
import com.example.sparkstask2.databinding.ActivityProfileDataBinding
import com.example.sparkstask2.model.ProfileObject
import com.example.sparkstask2.ui.sign_up.MainActivity


class ProfileData : AppCompatActivity() {
    private lateinit var  binding : ActivityProfileDataBinding
    private lateinit var  viewModel : ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialization
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_data)
        val application = requireNotNull(this).application
        val data =  intent.getParcelableExtra<ProfileObject>(this.resources.getString(R.string.OBJECT_NAME))
        val viewModelFactory = ViewModelFactory(application,data)
        viewModel = ViewModelProvider(this,viewModelFactory)[ProfileViewModel::class.java]
        binding.data = viewModel

        //Handle TopBar SignOut
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sign_out ->{
                    startActivity(Intent(this,MainActivity::class.java))
                    this.finish()
                    true
                }
                else -> false
            }}

    }
}