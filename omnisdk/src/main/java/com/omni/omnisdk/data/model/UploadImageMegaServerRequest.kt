package com.omni.omnisdk.data.model

class UploadImageMegaServerRequest {
    var startStep: String = ""
    var endStep: String = ""
    var image: ImageObject? = null
    var rotation: RotationObject? = null
}

class ImageObject {
    var s3Bucket: String = ""
    var s3Key: String = ""
}

class RotationObject {
    var rotation: Number ?= null
}
