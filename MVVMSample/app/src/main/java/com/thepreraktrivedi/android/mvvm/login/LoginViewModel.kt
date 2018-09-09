package com.thepreraktrivedi.android.mvvm.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepreraktrivedi.android.mvvm.feature.login.LoginManager
import com.thepreraktrivedi.android.mvvm.foundation.callback.ApiCallbackObserver
import com.thepreraktrivedi.android.mvvm.foundation.exceptions.ApiError
import com.thepreraktrivedi.android.mvvm.foundation.models.User
import com.thepreraktrivedi.android.mvvm.foundation.utils.Resource

class LoginViewModel : ViewModel() {

    val loginResponseData = MutableLiveData<Resource<User>>()

    private val loginManager by lazy { LoginManager.instance() }

    fun initLogin(username: String, password: String) {
        loginResponseData.postValue(Resource.loading(null))

        Log.d(TAG, "Initiating login")
        loginManager.doLogin(username, password, object : ApiCallbackObserver<User> {
            override fun onSuccess(user: User) {
                Log.d(TAG, "Login success: $user")
                loginResponseData.postValue(Resource.success(user))
            }

            override fun onFailure(apiError: ApiError) {
                Log.d(TAG, "Login failure: $apiError")
                loginResponseData.postValue(Resource.error(apiError))
            }
        })
    }

    companion object {
        private val TAG = LoginViewModel::class.java.simpleName
    }
}
