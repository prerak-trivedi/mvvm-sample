package com.thepreraktrivedi.android.mvvm.foundation.exceptions

data class ApiError(val errorCode: Int = ERROR_CODE_DEFAULT_CATCH_ALL,
                    val errorMessage: String): Exception(errorMessage) {

    companion object {
        const val ERROR_CODE_NO_INTERNET_CONNECTION: Int = -1
        const val ERROR_CODE_DEFAULT_CATCH_ALL = 500
        const val ERROR_CODE_BAD_DATA = 400
        const val ERROR_CODE_UNAUTHORIZED = 401
        const val ERROR_CODE_NOT_FOUND = 404

        fun defaultError(message: String): ApiError {
            return ApiError(ERROR_CODE_DEFAULT_CATCH_ALL, message)
        }

        fun authError(message: String): ApiError {
            return ApiError(ERROR_CODE_UNAUTHORIZED, message)
        }
    }
}