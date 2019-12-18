package com.omniocr.ui.home

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class HomeViewModel(val app: Application) : AndroidViewModel(app) {
    var onAssesmentClick = MutableLiveData<Boolean>()
    var nextActivityTocapture = MutableLiveData<Boolean>()
    var imageList = MutableLiveData<ArrayList<String>>()

    fun onAssessmentClicked(viw: View) {
        onAssesmentClick.value = true
    }

    fun permissionDranted() {
        nextActivityTocapture.value = true
    }

    fun onReceiveCapturedImage(capturedImageList: ArrayList<String>?) {
        if (capturedImageList?.size!! > 0) {
            imageList.value = capturedImageList
        }
    }


}

