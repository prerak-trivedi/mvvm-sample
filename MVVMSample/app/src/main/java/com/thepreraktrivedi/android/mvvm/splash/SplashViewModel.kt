package com.thepreraktrivedi.android.mvvm.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepreraktrivedi.android.mvvm.foundation.utils.Resource
import com.thepreraktrivedi.android.mvvm.foundation.utils.SingleLiveEvent
import java.util.concurrent.ThreadLocalRandom

class SplashViewModel : ViewModel() {

    // Data Streams
    val titleText = MutableLiveData<Resource<String>>()

    // Navigation Commands
    val launchLoginActivityCommand = SingleLiveEvent<Void>()
    val launchLandingActivityCommand = SingleLiveEvent<Void>()

    fun init() {
        titleText.postValue(Resource.loading("Loading!"))

        Handler().postDelayed({
            titleText.postValue(Resource.error(IllegalArgumentException(
                    "Oops, Something went wrong!"), null))
        }, 3000)
    }

    fun retry() {
        titleText.postValue(Resource.loading("Loading!"))

        Handler().postDelayed({
            titleText.postValue(Resource.success("Success!"))
        }, 3000)
    }

    fun requestNextStep() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            launchLoginActivityCommand.call()
        } else {
            launchLandingActivityCommand.call()
        }
    }
}
