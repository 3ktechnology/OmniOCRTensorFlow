package com.omniocr.ui.review

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omniocr.data.model.CameraModel
import org.json.JSONObject

class ReviewViewModel(val app: Application) : AndroidViewModel(app) {

    var isSelectClicked = MutableLiveData<Boolean>()
    var isBackClicked = MutableLiveData<Boolean>()
    var isSelectAllClicked = MutableLiveData<Boolean>()
    var isSelectEnabled = MutableLiveData<Boolean>()
    var isDeleteClicked = MutableLiveData<Boolean>()
    var isReCaptureClicked = MutableLiveData<Boolean>()
    var isAllFormDeleted = MutableLiveData<Boolean>()
    var formDeletePerformed = MutableLiveData<Boolean>()
    var uploadImageToMegaServer = MutableLiveData<Boolean>()
    var model = CameraModel()
    var capturedImageList = ArrayList<CameraModel>()
    var updateImageList = MutableLiveData<ArrayList<CameraModel>>()
    var imageUploadResponseId = MutableLiveData<String>()

    fun selectClicked(view: View) {
        isSelectClicked.value = true
    }

    fun cancellClicked(view: View) {
        isSelectClicked.value = false
        isSelectAllClicked.value = false
        isSelectEnabled.value = false
    }

    fun backClicked(view: View) {
        isBackClicked.value = true
    }

    fun onSelectAllClicked(view: View) {

        isSelectAllClicked.value = true
        isSelectEnabled.value = true

    }

    fun deleteClicked(view: View) {
        isDeleteClicked.value = true
    }

    fun notSelected(view: View) {

    }

    fun reCaptureImage(view: View) {
        deleteAllSelectedImage()

    }

    fun disableClick(view: View) {
    }

    fun allFormDeleted(allFormDeleted: Boolean) {
        isAllFormDeleted.value = allFormDeleted
        isSelectEnabled.value = false
        updateImageList.value = capturedImageList

    }

    fun onNextClck(view: View) {
        uploadImageToMegaServer.value = true

    }

    private fun deleteAllSelectedImage() {
        for (model in ArrayList(capturedImageList)) {
            if (model.selected) {
                capturedImageList.remove(model)
            }
        }
        updateImageList.value = capturedImageList
        isReCaptureClicked.value = true

    }

    fun dontClick(view: View) {
    }


    fun deleteFormFromList(capturedImageList: ArrayList<CameraModel>) {
        for (model in ArrayList(capturedImageList)) {
            if (model.selected) {
                capturedImageList.remove(model)
            }
        }

        if (capturedImageList.size > 0) {
            allFormDeleted(false)
        } else {
            allFormDeleted(true)
        }


        formDeletePerformed.value = true
    }

    fun enableSelect(capturedImageList: ArrayList<CameraModel>?) {
        val selectedList = ArrayList<Int>()
        for (i in 0 until capturedImageList!!.size) {
            if (capturedImageList[i].selected)
                selectedList.add(i)
            else selectedList.remove(i)
        }
        when {
            capturedImageList.size == selectedList.size -> {
                isSelectClicked.value = true
                isSelectEnabled.value = true
                isSelectAllClicked.value = true
            }
            selectedList.size > 0 -> {
                isSelectClicked.value = true
                isSelectEnabled.value = true
            }
            else -> {
                isSelectClicked.value = false
                isSelectAllClicked.value = false
                isSelectEnabled.value = false
            }
        }

    }

    fun getImageListFromIntent(intentImage: ArrayList<String>?) {
        for (i in 0 until intentImage!!.size) {
            capturedImageList.add(CameraModel(intentImage[i], false))
        }
        updateImageList.value = capturedImageList

    }

    fun onReceiveCapturedImage(capturdImageList: ArrayList<String>?) {
        for (i in 0 until capturdImageList!!.size) {
            capturedImageList.add(CameraModel(capturdImageList[i], false))
        }

        updateImageList.value = capturedImageList
    }

    fun receivedUploadImageId(serverResponse: String?) {
        val jsObject = JSONObject(serverResponse)
        val id = jsObject.optString("id")
        val internal_trace_id =
            jsObject.getJSONObject("support_info").optString("internal_trace_id")

        imageUploadResponseId.value = id
    }


}