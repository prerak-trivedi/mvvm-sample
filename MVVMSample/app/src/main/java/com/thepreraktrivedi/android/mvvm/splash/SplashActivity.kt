package com.thepreraktrivedi.android.mvvm.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.thepreraktrivedi.android.mvvm.R
import com.thepreraktrivedi.android.mvvm.base.BaseActivity
import com.thepreraktrivedi.android.mvvm.foundation.utils.NetworkLiveData
import com.thepreraktrivedi.android.mvvm.foundation.utils.Resource
import com.thepreraktrivedi.android.mvvm.landing.LandingActivity
import com.thepreraktrivedi.android.mvvm.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViewModel()
        setListeners()
        splashViewModel.init()
    }

    private fun setListeners() {
        retryButton.setOnClickListener {
            splashViewModel.retry()
        }
    }

    private fun initViewModel() {
        val observer = this@SplashActivity

        splashViewModel = ViewModelProviders.of(observer).get(SplashViewModel::class.java)

        splashViewModel.run {

            // Observe Data Stream Changes
            titleText.observe(observer, Observer {
                handleTitleChanges(it)
            })

            // Handle Navigation Commands
            launchLoginActivityCommand.observe(observer, Observer {
                loadNextActivity(true)
            })

            launchLandingActivityCommand.observe(observer, Observer {
                loadNextActivity(false)
            })
        }
    }

    private fun handleTitleChanges(resource: Resource<String>?) {

        val status = resource?.status ?: Resource.Status.ERROR

        when (status) {
            Resource.Status.SUCCESS -> {
                statusTextView.text = resource?.data ?: ""
                progressLoader.visibility = View.GONE
                Handler().postDelayed({ splashViewModel.requestNextStep() }, 3000)
                retryButton.visibility = View.GONE
            }

            Resource.Status.ERROR -> {
                statusTextView.text = resource?.error?.message
                progressLoader.visibility = View.GONE
                retryButton.visibility = View.VISIBLE
            }

            Resource.Status.LOADING -> {
                statusTextView.text = resource?.data
                progressLoader.visibility = View.VISIBLE
                retryButton.visibility = View.GONE
            }
        }
    }

    private fun loadNextActivity(isLogin: Boolean) {
        val clz = if (isLogin) LoginActivity::class.java else LandingActivity::class.java
        val msg = "Proceeding to ${if (isLogin) "Login" else "Landing"} Screen..."
        statusTextView.text = msg

        Handler().postDelayed({
            startActivity(Intent(this, clz))
            finish()
        }, 3000)
    }

    override fun onNetworkConnectivityChanged(networkMetaData: NetworkLiveData.NetworkMetaData?) {
        Log.d(TAG,"Network Connected: ${networkMetaData?.isConnected}")
    }

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
    }
}
