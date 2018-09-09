package com.thepreraktrivedi.android.mvvm.login

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.thepreraktrivedi.android.mvvm.R
import com.thepreraktrivedi.android.mvvm.base.BaseActivity
import com.thepreraktrivedi.android.mvvm.foundation.utils.NetworkLiveData
import com.thepreraktrivedi.android.mvvm.foundation.utils.Resource
import com.thepreraktrivedi.android.mvvm.foundation.utils.toastShort
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        loginButton.setOnClickListener { doLogin() }
    }

    private fun initViewModel() {
        val owner = this@LoginActivity

        loginViewModel = ViewModelProviders.of(owner).get(LoginViewModel::class.java)
        loginViewModel.run {

            // Observe Data Stream Changes
            loginResponseData.observe(owner, Observer {
                val status = it?.status ?: Resource.Status.ERROR

                when (status) {
                    Resource.Status.LOADING -> { /** update progress bar **/ }
                    Resource.Status.ERROR -> { /** handle login error **/ }
                    Resource.Status.SUCCESS -> { toastShort("Success: ${it.data}") }
                }
            })
        }
    }

    private fun doLogin() {
        loginViewModel.initLogin("abc", "xyz")
    }

    override fun onNetworkConnectivityChanged(networkMetaData: NetworkLiveData.NetworkMetaData?) {
        Log.d(TAG,"Network Connected: ${networkMetaData?.isConnected}")
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }
}
