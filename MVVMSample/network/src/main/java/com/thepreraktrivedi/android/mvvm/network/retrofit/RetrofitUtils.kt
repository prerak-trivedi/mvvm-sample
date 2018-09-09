package com.thepreraktrivedi.android.mvvm.network.retrofit

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.GsonBuilder
import com.thepreraktrivedi.android.mvvm.foundation.callback.ApiCallbackObserver
import com.thepreraktrivedi.android.mvvm.foundation.exceptions.ApiError
import com.thepreraktrivedi.android.mvvm.network.rxutils.SimpleObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtils {

    private const val BASE_URL = "http://www.mocky.io/v2/"
    private val CONNECT_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10)
    private val WAIT_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(15)

    fun buildRetrofit(): Retrofit = buildRetrofit(BASE_URL)

    fun buildRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .baseUrl(baseUrl)
            .build()

    private fun createOkHttpClient() = OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        readTimeout(WAIT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        writeTimeout(WAIT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()

    private fun getGson() = GsonBuilder().create()

    /**
     * Converts Throwable from RxJava Observer into the NetworkError class
     */
    fun convertThrowableToApiError(e: Throwable): ApiError {
        Log.d(TAG, "Type of error: " + e.javaClass.name)
        if (e is ApiError) {
            return e
        }
        if (e is HttpException) {
            Log.d(TAG, "API Error\n " + e.code() + ":" + e.message())
            return ApiError(e.code(), e.message())
        }
        return ApiError.defaultError(e.message ?: "")
    }

    fun <T> subscribeObservable(observable: Observable<T>, callback: ApiCallbackObserver<T>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SimpleObserver(callback))
    }
}