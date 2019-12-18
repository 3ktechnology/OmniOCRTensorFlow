package com.omni.omnisdk.upload_images_to_server

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.omni.omnisdk.data.repository.NetworkInterface
import org.junit.Before
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UploadImageViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var application: Application

    @InjectMocks
    lateinit var uploadImageViewModel: UploadImageViewModel

    @Mock
    lateinit var networkInterface : NetworkInterface

    var imgList= ArrayList<String>()

    @Before
    fun setUp() {
        application = Mockito.mock(Application::class.java)
        uploadImageViewModel = UploadImageViewModel(application)
        networkInterface = Mockito.mock(NetworkInterface::class.java)

        imgList.add("/storage/emulated/0/DCIM/Camera/IMG_20191101_145515715.jpg")

    }

    @Test(expected = RuntimeException::class)
    fun uploadImage() {

        uploadImageViewModel.uploadImageUsingBase64(imgList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun uploadFile() {

        /***
         * mockito cannot test private methods of a class: so remove private access specifier
         */
        uploadImageViewModel.uploadFile("/storage/emulated/0/DCIM/Camera/IMG_20191101_145515715.jpg")
    }

    @Test(expected = ExceptionInInitializerError::class)
    fun uploadImagesToMegaServer() {
        /***
         * mockito cannot test private methods of a class: so remove private access specifier
         */
        uploadImageViewModel.uploadImagesToMegaServer()
    }

    @Test
    fun onStateChanged() {

        var transferState = TransferState.COMPLETED
        uploadImageViewModel.onStateChange(1,transferState)
        assertEquals(1,uploadImageViewModel.count)
    }

    @Test
    fun onStateChangedFileTransferStateFailed() {

        var transferState = TransferState.FAILED
        uploadImageViewModel.onStateChange(1,transferState)
    }

}