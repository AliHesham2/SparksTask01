package com.example.sparkstask2.ui.sign_up

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sparkstask2.R
import com.example.sparkstask2.model.ProfileObject
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(private val app:Application):AndroidViewModel(app) {


    fun callFacebook(accessToken: AccessToken , userData : (data:ProfileObject) ->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            facebookDataRequest(accessToken){
                userData(it)
            }
        }
    }

    private  fun facebookDataRequest(accessToken: AccessToken,userData : (data:ProfileObject) ->Unit) {
        val graphRequest =GraphRequest.newMeRequest(accessToken){ obj, _ ->
            try {
                userData(ProfileObject(obj!!.getString(app.resources.getString(R.string.name_req)),obj.getString(app.resources.getString(R.string.email_req)),JSONObject(obj.getString(app.resources.getString(R.string.pic_req))).getJSONObject(app.resources.getString(R.string.data_req)).getString(app.resources.getString(R.string.url_req)).toUri()))
            }catch (e:Exception){
                disconnectFromFacebook()
            }
        }
        val param = Bundle()
        param.putString("fields","name,email,id,picture.type(large),gender,birthday")
        graphRequest.parameters = param
        graphRequest.executeAsync()
    }

    private fun disconnectFromFacebook() {
        GraphRequest(AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE,
            { LoginManager.getInstance().logOut() })
            .executeAsync()
    }
}