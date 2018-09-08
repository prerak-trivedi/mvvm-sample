package com.thepreraktrivedi.android.mvvm.utils

/**
 * Inspired by https://developer.android.com/topic/libraries/architecture/guide.html#addendum.
 *
 * This represents a simple resource from the network that our services will return
 * to indicate success or failure.
 */
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