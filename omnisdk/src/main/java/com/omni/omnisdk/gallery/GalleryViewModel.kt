package com.omni.omnisdk.gallery

import android.app.Application
import android.database.Cursor
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omni.omnisdk.data.model.GalleryModel

class GalleryViewModel(val app: Application) : AndroidViewModel(app) {
    private var isLongClicked: Boolean = false
    private val selectedImageList = ArrayList<GalleryModel>()
    var isNotifyAdapter = MutableLiveData<Boolean>()
    var singleClickedImage = MutableLiveData<ArrayList<String>>()
    var toolbarVisibility = MutableLiveData<Boolean>()
    var count = MutableLiveData<String>()
    private var tempList = ArrayList<String>()
    var imageCountReached = MutableLiveData<Int>()


    fun updateClick(pos: Int, imageList: ArrayList<GalleryModel>) {
        if (selectedImageList.size > 19) {
            imageCountReached.value = selectedImageList.size
        }
        if (isLongClicked) {
            if (imageList[pos].getSelected()) {
                imageList[pos].setSelected(false)
                for (i in 0 until selectedImageList.size)
                    if (imageList[pos].image == selectedImageList[i].image) {
                        selectedImageList.removeAt(i)
                        break
                    }
            } else {
                imageList[pos].setSelected(true)
                selectedImageList.add(GalleryModel(imageList[pos].image, true))
            }
            if (selectedImageList.size == 0) {
                isLongClicked = false
                toolbarVisibility.value = false
            }
            isNotifyAdapter.value = true
            count.value = selectedImageList.size.toString()
        } else {
            selectedImageList.add(GalleryModel(imageList[pos].image, false))
            getRealPathFromUri(selectedImageList)
        }
    }

    fun updateLongClick(pos: Int, imageList: ArrayList<GalleryModel>) {
        isLongClicked = true
        imageList[pos].setSelected(true)
        selectedImageList.add(GalleryModel(imageList[pos].image, true))
        isNotifyAdapter.value = true
        toolbarVisibility.value = true
        count.value = selectedImageList.size.toString()
    }

    fun onOkClicked(view: View) {
        getRealPathFromUri(selectedImageList)
    }


    /*private*/ fun getRealPathFromUri(list: ArrayList<GalleryModel>) {
        for (i in 0 until list.size) {
            var cursor: Cursor? = null
            var realPath: String? = null
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = app.contentResolver.query(list[i].image, proj, null, null, null)
                val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                realPath = cursor.getString(columnIndex)
            } finally {
                cursor?.close()
            }

            if (realPath != null) {
                tempList.add(realPath)
            }
        }
        singleClickedImage.value = tempList
    }


}