package com.thepreraktrivedi.android.mvvm.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.thepreraktrivedi.android.mvvm.foundation.utils.NetworkLiveData

/**
 * Keeps a track of whether internet is connected.
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var networkLiveData: NetworkLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        subscribeToNetworkChanges()
    }

    private fun subscribeToNetworkChanges() {
        networkLiveData = NetworkLiveData(this.applicationContext)
        networkLiveData.observe(this, Observer<NetworkLiveData.NetworkMetaData> {
            networkMetaData -> onNetworkConnectivityChanged(networkMetaData)
        })
    }

    protected fun loadAndReplaceFragment(fragment: Fragment,
                                         @IdRes resourceId: Int,
                                         backstackTag: String?,
                                         sharedViewList: List<View>?) {

        if (!isActivityActive(this)) {
            return
        }

        with (supportFragmentManager.beginTransaction()) {
            replace(resourceId, fragment)
            if (!TextUtils.isEmpty(backstackTag)) {
                addToBackStack(backstackTag)
            } else {
                disallowAddToBackStack()
            }
            sharedViewList?.let {
                it.forEach {
                    addSharedElement(it, ViewCompat.getTransitionName(it)!!)
                }
            }
            commitAllowingStateLoss()
        }
    }

    protected fun isActivityActive(activity: BaseActivity?) =
            !(activity?.isDestroyed!! || activity.isFinishing)

    abstract fun onNetworkConnectivityChanged(networkMetaData: NetworkLiveData.NetworkMetaData?)
}