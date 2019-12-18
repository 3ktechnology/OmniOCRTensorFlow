package com.omni.omnisdk.data.model

import com.google.gson.annotations.SerializedName

class UploadImagesUsingBase64Request {

    @SerializedName("start_step")
    var start_step: String? = null
    @SerializedName("end_step")
    var end_step: String? = null
    @SerializedName("rotation")
    var rotation: RotationModel? = null
    @SerializedName("image")
    var image: ImageModel? = null
}

class ImageModel {
    @SerializedName("image_base64")
    var image_base64: String? = null
}

class RotationModel {
    @SerializedName("rotation")
    var rotation: Number? = null
}
