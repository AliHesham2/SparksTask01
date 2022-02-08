package com.example.sparkstask2.ui.sign_up

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sparkstask2.R
import com.example.sparkstask2.databinding.ActivityMainBinding
import com.example.sparkstask2.model.ProfileObject
import com.example.sparkstask2.ui.home.ProfileData
import com.example.sparkstask2.ui.util.PopUpMsg
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task



class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private lateinit var  viewModel : MainViewModel
    private lateinit var callbackManager: CallbackManager
    private lateinit var loginManager: LoginManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialization
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //Google Configuration
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //FaceBook Configuration
         callbackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()


        //Handle onFaceButtonClick
        binding.faceButton.setOnClickListener {
            loginManager.logInWithReadPermissions(this, listOf(this.resources.getString(R.string.public_req),this.resources.getString(R.string.email_req)))
            handleFaceBookSignInResult()
        }
        //Handle onGoogleButtonClick
        binding.googleButton.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            googleResponse.launch(signInIntent)
        }
    }


    //Handle Google Profile result
    private val googleResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleGoogleSignInResult(task)
        }
    }

    //Handle Google Profile Data
    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            toProfileActivity(account,null)
        } catch (e: ApiException) {
            PopUpMsg.toastMsg(this@MainActivity,this@MainActivity.resources.getString(R.string.error))
        }
    }

    //Handle FaceBook Profile Data
 private fun handleFaceBookSignInResult(){
     loginManager.registerCallback(callbackManager, object :
         FacebookCallback<LoginResult?> {
         override fun onSuccess(loginResult: LoginResult?) {
             viewModel.callFacebook(loginResult!!.accessToken){
                 toProfileActivity(null,it)
             }
         }
         override fun onCancel() {
             PopUpMsg.toastMsg(this@MainActivity,this@MainActivity.resources.getString(R.string.error))
         }
         override fun onError(exception: FacebookException) {
            PopUpMsg.toastMsg(this@MainActivity,this@MainActivity.resources.getString(R.string.error))
         }
     })
 }
    //Pass User data to callbackManager
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    //Create object of user data
    private fun createUserDataObject(account: GoogleSignInAccount?,userData:ProfileObject? ):ProfileObject {
        return if (account != null){
            ProfileObject(account.displayName!!,account.email!!,account.photoUrl)
        }else{
            userData!!
        }
    }

    // move from this activity to ProfileActivity
    private fun toProfileActivity(account: GoogleSignInAccount?,userData:ProfileObject?){
        val intent = Intent(this,ProfileData::class.java)
        intent.putExtra(this.resources.getString(R.string.OBJECT_NAME),createUserDataObject(account,userData))
        startActivity(intent)
        finish()
    }



}