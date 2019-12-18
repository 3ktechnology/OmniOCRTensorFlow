package com.omni.omnisdk.capture

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.omni.omnisdk.R
import org.junit.Before
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CaptureImageViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var view: View

    @Mock
    lateinit var bitmap:Bitmap


    lateinit var captureImageViewModel: CaptureImageViewModel

    var DUMMY_STRING ="FAKE"

    @Before
    fun setUp() {
        application = Mockito.mock(Application::class.java)
        view = Mockito.mock(View::class.java)
        bitmap = Mockito.mock(Bitmap::class.java)
        captureImageViewModel = CaptureImageViewModel(application)
//        `when`(application.getString(R.string.no_image_clicked_error_msg)).thenReturn(DUMMY_STRING)

    }

    @Test
    fun onStandardClicked() {
        captureImageViewModel.onStandardClicked(view)
        assertEquals( true,captureImageViewModel.onStandardButtonClick.value)
        assertEquals(CaptureImageViewModel.ClickState.STANDARD,captureImageViewModel.onClickState.value)
    }

    @Test
    fun onArClicked() {
        captureImageViewModel.onArClicked(view)
        assertEquals(false,captureImageViewModel.onStandardButtonClick.value)
        assertEquals(CaptureImageViewModel.ClickState.AR, captureImageViewModel.onClickState.value)
    }

    @Test
    fun onGalleryClicked() {
        captureImageViewModel.onGalleryClicked(view)
        assertEquals(CaptureImageViewModel.ClickState.GALLARY,captureImageViewModel.onClickState.value)
    }

    @Test
    fun onCameraClicked() {
        captureImageViewModel.onCameraClicked(view)
        assertEquals(CaptureImageViewModel.ClickState.CAMERA,captureImageViewModel.onClickState.value)
    }

    @Test
    fun onNextClicked() {
        captureImageViewModel.onNextClicked(view)
        assertEquals(CaptureImageViewModel.ClickState.NEXT,captureImageViewModel.onClickState.value)
    }

    @Test
    fun performOnStandardButtonClick() {
        captureImageViewModel.performOnStandardButtonClick()
        assertEquals(true, captureImageViewModel.onStandardButtonClick.value)
    }


    @Test(expected = NullPointerException::class)
    fun displayEMptyMessage() {
        captureImageViewModel.displayEMptyMessage(view)
    }


    @Test(expected = RuntimeException::class)
    fun onImageCapturedThroughCamera() {
        var path = "/omni/new"
        var imageQuality =40
        var width=0
        var height=0
        captureImageViewModel.onImageCapturedThroughCamera(bitmap,path,imageQuality,width,height)
    }

//        fun onImageCapturedThroughAR(
//        filePath: String?) {
//        if (filePath != null) {
//            capturedImage.add(filePath)
//        }
//        updateViewAfterImageCaptured()
//    }


    @Test(expected = NullPointerException::class)
    fun onImageSelectedFromGallery() {

        var galleryImageList =ArrayList<String>()

        galleryImageList.add("/storage/emulated/0/Pictures/Screenshots/Screenshot_20191126-111051.png")
        galleryImageList.add("/storage/emulated/0/Pictures/Screenshots/Screenshot_20191127-182241.png")
        galleryImageList.add("/storage/emulated/0/Pictures/Screenshots/Screenshot_20191128-221144.png")

        captureImageViewModel.onImageSelectedFromGallery(galleryImageList)
//        `when`(application.resources.getString(R.string.title_next)).thenReturn("Next(3)")
//        captureImageViewModel.onImageSelectedFromGallery(galleryImageList)

    }


//
//    fun updateViewAfterImageCaptured() {
//        if (capturedImage.size > 0) {
//            imageCountText.value =
//                app.resources.getString(R.string.title_next) + " (" + capturedImage.size + ") "
//        } else {
//            imageCountText.value = app.resources.getString(R.string.title_next) + "(0)"
//        }
//        imageCaptureListSize.value = capturedImage.size
//        capturedImageList.value = capturedImage
//    }


//   bitmap -> android.graphics.Bitmap@c852b85
//    path-> /omni/new
//    /storage/emulated/0/Pictures/Screenshots/Screenshot_20191126-111051.png
//    /storage/emulated/0/Pictures/Screenshots/Screenshot_20191127-182241.png
//    /storage/emulated/0/Pictures/Screenshots/Screenshot_20191128-221144.png

}