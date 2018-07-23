package com.thepreraktrivedi.android.mvvm.login

import android.os.Bundle
import android.util.Log
import com.thepreraktrivedi.android.mvvm.R
import com.thepreraktrivedi.android.mvvm.base.BaseActivity
import com.thepreraktrivedi.android.mvvm.utils.NetworkLiveData

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onNetworkConnectivityChanged(networkMetaData: NetworkLiveData.NetworkMetaData?) {
        Log.d(TAG,"Network Connected: ${networkMetaData?.isConnected}")
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }
}
