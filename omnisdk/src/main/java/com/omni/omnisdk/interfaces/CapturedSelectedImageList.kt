package com.omni.omnisdk.interfaces

import android.net.Uri

interface CapturedSelectedImageList {

    fun onCapturedOrSelected(imageList: ArrayList<Uri>)
}