package com.thepreraktrivedi.android.mvvm.network.services

import com.thepreraktrivedi.android.mvvm.foundation.models.LoginRequest
import com.thepreraktrivedi.android.mvvm.foundation.models.User
import com.thepreraktrivedi.android.mvvm.network.retrofit.RetrofitUtils
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("5b9464503200004f007a667e")
    fun login(@Body request: LoginRequest,
              @Query("mocky-delay") delay: String = "2500ms"): Call<User>

    companion object {
        fun create() = RetrofitUtils.buildRetrofit().create(LoginService::class.java)
    }
}