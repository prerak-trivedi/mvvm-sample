package com.thepreraktrivedi.android.mvvm.foundation.callback

import com.thepreraktrivedi.android.mvvm.foundation.exceptions.ApiError

interface ApiCallbackObserver<T> {
    fun onFailure(e: ApiError) {}
    fun onSuccess(t: T) {}
}