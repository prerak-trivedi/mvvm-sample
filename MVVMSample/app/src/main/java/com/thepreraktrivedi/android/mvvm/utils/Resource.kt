package com.thepreraktrivedi.android.mvvm.utils

class Resource<T> private constructor(val status: Status,
                                      val data: T?,
                                      val error: Exception?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> error(error: Exception, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }
    }
}