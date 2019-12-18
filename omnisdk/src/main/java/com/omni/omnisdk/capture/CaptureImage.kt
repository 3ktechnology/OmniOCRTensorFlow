package com.omni.omnisdk.capture

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout.LayoutParams
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.omni.omnisdk.R
import com.omni.omnisdk.constants.ScanConstants
import com.omni.omnisdk.databinding.ActivityMainLibBinding
import com.omni.omnisdk.detection.DetectorActivity
import com.omni.omnisdk.gallery.GalleryActivity
import com.omni.omnisdk.utility.Constant
import com.omni.omnisdk.utility.OmniUtil
import com.omni.omnisdk.utility.OmniUtil.convertDpToPixel
import com.omni.omnisdk.utility.OmniUtil.getScreenSize
import com.omni.omnisdk.utility.OmniUtil.getSoftButtonsBarSizePort
import com.omni.omnisdk.utility.OmniUtil.rotate
import com.omni.omnisdk.utility.OmniUtil.showStatusBarr
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.error.CameraErrorListener
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.autoRedEye
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import kotlinx.android.synthetic.main.activity_main_lib.*
import java.util.*


class CaptureImage : AppCompatActivity() {


    private lateinit var viewModel: CaptureImageViewModel
    var handler2 = Handler()
    private var bottomBarHeight = 0
    private var fotoapparat: Fotoapparat? = null
    var runnable = Runnable {
        fotoapparat!!.start()
    }
    private var capturedImageList =
        ArrayList<String>()
    private var isClickEnabled = true
    private var options: Options? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showStatusBarr(this)
        val binding: ActivityMainLibBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_lib)
        viewModel = ViewModelProviders.of(this).get(CaptureImageViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        initialize()

        viewModel.onClickState.observe(this, androidx.lifecycle.Observer {
            when (it) {

                CaptureImageViewModel.ClickState.AR -> {
                    val intent =
                        Intent(this, DetectorActivity::class.java)
                    startActivityForResult(
                        intent,
                        AR_REQUEST_CODE
                    )


                }

                CaptureImageViewModel.ClickState.CAMERA -> captureStandardImage()
                CaptureImageViewModel.ClickState.NEXT -> reviewCapturedOrSelectedImages()
                CaptureImageViewModel.ClickState.GALLARY -> {
                    // capturedImageList.clear()
                    startActivityForResult(
                        Intent(this, GalleryActivity::class.java)
                            .putExtra
                                ("list", capturedImageList), GALLERY_REQUEST_CODE
                    )
                }
            }
        })

        viewModel.capturedImageList.observe(this, androidx.lifecycle.Observer {
            capturedImageList = it
        })

    }

    override fun onRestart() {
        super.onRestart()
        handler2.postDelayed(runnable, 0)
    }

    override fun onResume() {
        super.onResume()
        handler2.postDelayed(runnable, 0)
        viewModel.performOnStandardButtonClick()

    }

    override fun onPause() {
        fotoapparat!!.stop()
        super.onPause()
    }

    private fun initialize() {

        getScreenSize(this)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        try {
            options =
                intent.getSerializableExtra(OPTIONS) as Options
        } catch (e: Exception) {
            e.printStackTrace()
        }
        requestedOrientation = options!!.screenOrientation
        try {
            fotoapparat = Fotoapparat.with(this)
                .into(camera_view)
                .focusView(focusView)
                .previewScaleType(ScaleType.CenterCrop)
                .cameraErrorCallback(object : CameraErrorListener {
                    override fun onError(e: CameraException) {
                        Toast.makeText(
                            this@CaptureImage,
                            e.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        handler2.postDelayed(runnable, 0)
        fotoapparat!!.updateConfiguration(
            CameraConfiguration.builder().flash(autoRedEye()).build()
        )
        TOPBAR_HEIGHT = convertDpToPixel(56f, this@CaptureImage)
        val linearLayoutManager =
            LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        bottomBarHeight = getSoftButtonsBarSizePort(this)
        val lp = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 0, 0, bottomBarHeight)
        val cameraConfiguration =
            CameraConfiguration()
        if (options!!.isFrontfacing) {
            fotoapparat!!.switchTo(front(), cameraConfiguration)
        } else {
            fotoapparat!!.switchTo(back(), cameraConfiguration)
        }
        if (options!!.preSelectedUrls.size > options!!.count) {
            val large = options!!.preSelectedUrls.size - 1
            val small = options!!.count
            for (i in large downTo small - 1 + 1) {
                options!!.preSelectedUrls.removeAt(i)
            }
        }
        viewModel.updateViewAfterImageCaptured()
    }


    private fun reviewCapturedOrSelectedImages() {
        /* if (capturedImageList.size == 0) {
             Toast.makeText(
                 this@CaptureImage,
                 resources.getString(R.string.impty_image_list),
                 Toast.LENGTH_SHORT
             ).show()
         } else {
             startActivity(
                 Intent(
                     this@CaptureImage,
                     CapturedImageListActivity::class.java
                 ).putParcelableArrayListExtra("imageList", capturedImageList)
             )
             viewModel.clearImagelist()

         }*/


        val intent = Intent()
        intent.putExtra(Constant.EXTRA_ID, capturedImageList)
        setResult(Constant.IMAGERESULTCODE, intent)
        finish()


    }

    private fun captureStandardImage() {
        if (isClickEnabled) {
            isClickEnabled = false
            if (capturedImageList.size >= options!!.count) {
                OmniUtil.showToast(
                    this@CaptureImage,
                    String.format(
                        resources.getString(R.string.cannot_click_image_pix),
                        "" + options!!.count
                    )
                )
                isClickEnabled = true
                return
            }
            fotoapparat!!.takePicture().toBitmap()
                .transform { (bitmap, rotationDegrees) ->
                    rotate(
                        bitmap,
                        rotationDegrees
                    )
                }.whenAvailable { bitmap: Bitmap? ->
                    if (bitmap != null) {
                        synchronized(bitmap) {
                            onImageCaptured(
                                bitmap
                            )
                        }
                    }
                    null
                }
        }
    }

    private fun onImageCaptured(
        bitmap: Bitmap
    ) {
        viewModel.onImageCapturedThroughCamera(
            bitmap,
            resources.getString(R.string.sdcard_path),
            options!!.imageQuality,
            0,
            0
        )
        isClickEnabled = true
    }

    override fun onBackPressed() {
        if (capturedImageList.size > 0) {
            for (img in capturedImageList) {
                options!!.setPreSelectedUrls(ArrayList())
            }
            val anim: Animation = ScaleAnimation(
                1f,
                0f,  // Start and end values for the X axis scaling
                1f,
                0f,  // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF,
                0.5f,  // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF,
                0.5f
            ) // Pivot point of Y scaling

            anim.fillAfter = true // Needed to keep the result of the animation

            anim.duration = 300
            anim.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler2.removeCallbacks(runnable)
    }

    override fun onActivityResult(
        reqCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(reqCode, resultCode, data)
        when (reqCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.onImageSelectedFromGallery(
                        data!!.getStringArrayListExtra(
                            IMAGE_RESULTS
                        )
                    )
                }

            }

            AR_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.onImageCapturedThroughAR(data!!.extras!!.getString(ScanConstants.SCANNED_RESULT))
                    viewModel.performOnStandardButtonClick()
                }
            }
        }
    }

    companion object {
        private const val OPTIONS = "options"
        private const val AR_REQUEST_CODE = 103
        private const val GALLERY_REQUEST_CODE = 100
        var IMAGE_RESULTS = "image_results"
        var TOPBAR_HEIGHT = 0f

    }
}