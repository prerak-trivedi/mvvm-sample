package com.thepreraktrivedi.android.mvvm.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.thepreraktrivedi.android.mvvm.R
import com.thepreraktrivedi.android.mvvm.landing.LandingActivity
import com.thepreraktrivedi.android.mvvm.login.LoginActivity
import com.thepreraktrivedi.android.mvvm.utils.Resource
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViewModel()
        splashViewModel.init()
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

    private fun handleTitleChanges(it: Resource<String>?) {

        val status = it?.status ?: Resource.Status.ERROR

        when (status) {
            Resource.Status.SUCCESS -> {
                splashTitleTextView.text = it?.data
                progressLoader.visibility = View.GONE
                splashViewModel.requestNextStep()
            }

            Resource.Status.ERROR -> {
                splashTitleTextView.text = "Error"
                progressLoader.visibility = View.GONE
            }

            Resource.Status.LOADING -> {
                splashTitleTextView.text = it?.data
                progressLoader.visibility = View.VISIBLE
            }
        }
    }

    private fun loadNextActivity(isLogin: Boolean) {
        val clz = if (isLogin) LoginActivity::class.java else LandingActivity::class.java
        val msg = "Loading ${if (isLogin) "Login" else "Landing"}"
        splashTitleTextView.text = msg

        Handler().postDelayed({
            startActivity(Intent(this, clz))
            finish()
        }, 3000)
    }
}
