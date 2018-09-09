package com.thepreraktrivedi.android.mvvm.network.rxutils

import com.thepreraktrivedi.android.mvvm.foundation.callback.ApiCallbackObserver
import com.thepreraktrivedi.android.mvvm.network.retrofit.RetrofitUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class SimpleObserver<T>(private val callbackObserver: ApiCallbackObserver<T>) : Observer<T> {

    override fun onComplete() {
       // Do Nothing
    }

    override fun onSubscribe(d: Disposable) {
        // Do Nothing
    }

    override fun onNext(t: T) {
        callbackObserver.onSuccess(t)
    }

    override fun onError(e: Throwable) {
        callbackObserver.onFailure(RetrofitUtils.convertThrowableToApiError(e))
    }
}