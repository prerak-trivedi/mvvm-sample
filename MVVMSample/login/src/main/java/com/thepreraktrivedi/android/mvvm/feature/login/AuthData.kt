package com.thepreraktrivedi.android.mvvm.feature.login

import com.thepreraktrivedi.android.mvvm.foundation.models.User

object AuthData {

    var user: User? = null

    fun instance() = this

    fun clear() {
        user = null
    }
}