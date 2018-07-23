package com.thepreraktrivedi.android.mvvm.landing

import android.os.Bundle
import android.util.Log
import com.thepreraktrivedi.android.mvvm.R
import com.thepreraktrivedi.android.mvvm.base.BaseActivity
import com.thepreraktrivedi.android.mvvm.utils.NetworkLiveData

class LandingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
    }

    override fun onNetworkConnectivityChanged(networkMetaData: NetworkLiveData.NetworkMetaData?) {
        Log.d(TAG,"Network Connected: ${networkMetaData?.isConnected}")
    }

    companion object {
        private val TAG = LandingActivity::class.java.simpleName
    }
}
