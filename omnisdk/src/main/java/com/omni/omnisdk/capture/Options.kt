package com.omni.omnisdk.capture

import com.omni.omnisdk.utility.ImageQuality
import java.io.Serializable
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

class Options private constructor() : Serializable {
    var count = 1
        private set
    var path = "/DCIM/Camera"
        private set
    var imageQuality = 40
        private set
    var height = 0
        private set
    var width = 0
        private set
    var isFrontfacing = false
        private set
    var preSelectedUrls = ArrayList<String>()
        private set
    @ScreenOrientation
    var screenOrientation =
        SCREEN_ORIENTATION_UNSPECIFIED
        private set

    fun setPreSelectedUrls(preSelectedUrls: ArrayList<String>): Options {
        check()
        this.preSelectedUrls = preSelectedUrls
        return this
    }

    fun setImageQuality(imageQuality: ImageQuality): Options {
        if (imageQuality === ImageQuality.LOW) {
            this.imageQuality = 20
        } else if (imageQuality === ImageQuality.HIGH) {
            this.imageQuality = 80
        } else {
            this.imageQuality = 40
        }
        return this
    }

    fun setImageResolution(height: Int, width: Int): Options {
        check()
        if (height == 0 || width == 0) {
            throw NullPointerException("width or height can not be 0")
        }
        this.height = height
        this.width = width
        return this
    }

    fun setFrontfacing(frontfacing: Boolean): Options {
        isFrontfacing = frontfacing
        return this
    }

    private fun check() {
        if (this == null) {
            throw NullPointerException("call init() method to initialise Options class")
        }
    }

    fun setCount(count: Int): Options {
        check()
        this.count = count
        return this
    }

    fun getRequestCode(): Int {
        if (requestCode == 0) {
            throw NullPointerException("requestCode in Options class is null")
        }
        return requestCode
    }

    fun setRequestCode(requestcode: Int): Options {
        check()
        requestCode = requestcode
        return this
    }

    fun setPath(path: String): Options {
        check()
        this.path = path
        return this
    }

    fun setScreenOrientation(@ScreenOrientation screenOrientation: Int): Options {
        check()
        this.screenOrientation = screenOrientation
        return this
    }

    @Retention(RetentionPolicy.SOURCE)
    annotation class ScreenOrientation

    companion object {
        const val SCREEN_ORIENTATION_UNSPECIFIED = -1
        const val SCREEN_ORIENTATION_PORTRAIT = 1
        var requestCode = 0
        @JvmStatic
        fun init(): Options {
            return Options()
        }
    }
}