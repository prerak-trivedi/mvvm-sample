package com.thepreraktrivedi.android.mvvm.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData

/**
 * Observes the changes in Network Connectivity
 */
class NetworkLiveData(val context: Context) : LiveData<NetworkLiveData.NetworkMetaData>() {

    private val networkStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val networkMetaData = getNetworkMetaData(context)
            Log.d(TAG, "Network Status: $networkMetaData")
            postValue(networkMetaData)
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkStateReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkStateReceiver)
    }

    private fun getNetworkMetaData(context: Context) : NetworkMetaData {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        val isConnected = networkInfo?.isConnected ?: false
        var connectivityType = CONNECTIVITY_NONE
        when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> connectivityType = CONNECTIVITY_WIFI
            ConnectivityManager.TYPE_MOBILE -> connectivityType = CONNECTIVITY_LTE
        }
        return NetworkMetaData(connectivityType, isConnected)
    }

    companion object {
        private val TAG = NetworkLiveData::class.java.simpleName
        private const val CONNECTIVITY_NONE = "NONE"
        private const val CONNECTIVITY_WIFI = "WIFI"
        private const val CONNECTIVITY_LTE = "LTE"
    }

    data class NetworkMetaData(val type: String, val isConnected: Boolean)
}