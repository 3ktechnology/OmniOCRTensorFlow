package com.omni.omnisdk.capture

import android.app.Application
import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omni.omnisdk.R
import com.omni.omnisdk.utility.OmniUtil

class CaptureImageViewModel(val app: Application) : AndroidViewModel(app) {
    val onClickState = MutableLiveData<ClickState>()
    var onStandardButtonClick = MutableLiveData<Boolean>()
    var imageCaptureListSize = MutableLiveData<Int>()
    var imageCountText = MutableLiveData<String>()
    var capturedImage = ArrayList<String>()
    var capturedImageList = MutableLiveData<ArrayList<String>>()

    fun onStandardClicked(view: View) {
        onStandardButtonClick.value = true
        onClickState.value = ClickState.STANDARD
    }

    fun onArClicked(view: View) {
        onStandardButtonClick.value = false
        onClickState.value = ClickState.AR
    }

    fun onGalleryClicked(view: View) {
        onClickState.value = ClickState.GALLARY
    }

    fun onCameraClicked(view: View) {
        onClickState.value = ClickState.CAMERA
    }

    fun onNextClicked(view: View) {
        onClickState.value = ClickState.NEXT
    }

    fun displayEMptyMessage(view : View) {
        OmniUtil.showToast(app, app.resources.getString(R.string.no_image_clicked_error_msg))
    }

    fun performOnStandardButtonClick() {
        onStandardButtonClick.value = true
    }


    fun onImageCapturedThroughCamera(
        bitmap: Bitmap,
        path: String,
        imageQuality: Int,
        width: Int,
        height: Int
    ) {
        capturedImage.add(
            OmniUtil.writeImage(
                bitmap, path, imageQuality, width, height
            ).toString()
        )
        updateViewAfterImageCaptured()
    }

    fun onImageCapturedThroughAR(
        filePath: String?
    ) {
        if (filePath != null) {
            capturedImage.add(filePath)
        }
        updateViewAfterImageCaptured()
    }

    fun onImageSelectedFromGallery(galleryImageList: java.util.ArrayList<String>?) {
        var i = 0
        if (galleryImageList != null) {
            while (i < galleryImageList.size) {
                capturedImage.add(galleryImageList[i])
                i++
            }
        }
        updateViewAfterImageCaptured()

    }

    fun updateViewAfterImageCaptured() {
        if (capturedImage.size > 0) {
            imageCountText.value =
                app.resources.getString(R.string.title_next) + " (" + capturedImage.size + ") "
        } else {
            imageCountText.value = app.resources.getString(R.string.title_next) + "(0)"
        }
        imageCaptureListSize.value = capturedImage.size
        capturedImageList.value = capturedImage
    }


    enum class ClickState {
        STANDARD,
        AR,
        GALLARY,
        CAMERA,
        NEXT
    }
}