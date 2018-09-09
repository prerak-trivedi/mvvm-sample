package com.thepreraktrivedi.android.mvvm.feature.login

import android.text.TextUtils
import com.thepreraktrivedi.android.mvvm.foundation.callback.ApiCallbackObserver
import com.thepreraktrivedi.android.mvvm.foundation.exceptions.ApiError
import com.thepreraktrivedi.android.mvvm.foundation.models.LoginRequest
import com.thepreraktrivedi.android.mvvm.foundation.models.User
import com.thepreraktrivedi.android.mvvm.network.retrofit.RetrofitUtils
import com.thepreraktrivedi.android.mvvm.network.services.LoginService
import io.reactivex.Observable

object LoginManager {

    private val loginService by lazy { LoginService.create() }

    fun instance() = this

    // TODO - Add caching logic, auth management, etc.

    // Sends a mock response for now, ideally this should be real API call via Retrofit
    fun doLogin(username: String, password: String, callback: ApiCallbackObserver<User>) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            callback.onFailure(ApiError(-1, "Invalid input data"))
            return
        }

        val loginObservable = Observable.just(LoginRequest(username, password))
                .map<User> { request: LoginRequest ->
                    // Sync call is fine because this is on a separate thread via RxJava
                    with(loginService.login(request).execute()) {
                        if (!isSuccessful || body() == null) {
                            throw ApiError.defaultError("Login was not successful")
                        }
                        body()
                    }
                }
        RetrofitUtils.subscribeObservable(loginObservable, callback)
    }
}