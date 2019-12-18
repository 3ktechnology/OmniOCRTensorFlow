package com.omniocr.omniocr.review

import android.app.Application
import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.omniocr.data.model.CameraModel
import com.omniocr.ui.review.ReviewViewModel
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReviewViewModelTest
{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var application:Application

    @Mock
    lateinit var view:View

    lateinit var reviewViewModel : ReviewViewModel


    @Before
    fun setup(){
        application =Mockito.mock(Application::class.java)
        view = Mockito.mock(View::class.java)
        reviewViewModel =ReviewViewModel(application)
    }

    @Test
    fun selectClicked(){
        reviewViewModel.selectClicked(view)
        assertEquals(true,reviewViewModel.isSelectClicked.value)
    }

    @Test
    fun cancellClicked() {
        reviewViewModel.cancellClicked(view)
        assertEquals(false,reviewViewModel.isSelectClicked.value)
        assertEquals(false,reviewViewModel.isSelectAllClicked.value)
        assertEquals(false,reviewViewModel.isSelectEnabled.value)
    }

    @Test
    fun backClicked() {
        reviewViewModel.backClicked(view)
        assertEquals(true,reviewViewModel.isBackClicked.value)
    }

    @Test
    fun onSelectAllClicked() {
        reviewViewModel.onSelectAllClicked(view)
        assertEquals(true,reviewViewModel.isSelectAllClicked.value)
        assertEquals(true,reviewViewModel.isSelectEnabled.value)

    }

    @Test
    fun deleteClicked() {
        reviewViewModel.deleteClicked(view)
        assertEquals(true,reviewViewModel.isDeleteClicked.value)
    }


    @Test
    fun reCaptureImage() {
        reviewViewModel.reCaptureImage(view)
    }


    @Test
    fun onNextClck() {
        reviewViewModel.onNextClck(view)
        assertEquals(true,reviewViewModel.uploadImageToMegaServer.value)
    }

    @Test
    fun deleteFormFromList()
    {

        var cameraModel=CameraModel("abcdwed",true)
        var capturedImageList =ArrayList<CameraModel>()
        capturedImageList.add(cameraModel)
        reviewViewModel.deleteFormFromList(capturedImageList)
        assertEquals(true,reviewViewModel.formDeletePerformed.value)
        assertEquals(true,reviewViewModel.isAllFormDeleted.value)
        assertEquals(false,reviewViewModel.isSelectEnabled.value)

    }

    @Test
    fun enableSelect_capturedListSizeEqualsSelectedImageList()
    {
        var capturedImageList =ArrayList<CameraModel>()
        var cameraModel=CameraModel("abcdwed",true)
        capturedImageList.add(cameraModel)
        reviewViewModel.enableSelect(capturedImageList)

        assertEquals(true,reviewViewModel.isSelectClicked.value)
        assertEquals(true,reviewViewModel.isSelectEnabled.value)
        assertEquals(true,reviewViewModel.isSelectAllClicked.value)
    }

    @Test
    fun enableSelect_capturedListSizeGreaterThanZero()
    {
        var capturedImageList =ArrayList<CameraModel>()
        var cameraModel=CameraModel("abcdwed",true)
        var cameraModel1=CameraModel("pqrst",true)
        var cameraModel2=CameraModel("pqreest",false)

        capturedImageList.add(cameraModel)
        capturedImageList.add(cameraModel1)
        capturedImageList.add(cameraModel2)

        reviewViewModel.enableSelect(capturedImageList)


        assertEquals(true,reviewViewModel.isSelectClicked.value)
        assertEquals(true,reviewViewModel.isSelectEnabled.value)
    }

    @Test
    fun enableSelect_capturedListEmpty()
    {
        var capturedImageList =ArrayList<CameraModel>()
        var cameraModel=CameraModel("asdsad",false)
        capturedImageList.add(cameraModel)
        reviewViewModel.enableSelect(capturedImageList)
        assertEquals(false,reviewViewModel.isSelectClicked.value)
        assertEquals(false,reviewViewModel.isSelectEnabled.value)
        assertEquals(false,reviewViewModel.isSelectAllClicked.value)

    }

    @Test
    fun onReceiveCapturedImage(){
        var capturedImageList =ArrayList<String>()
        capturedImageList.add("abcdefgh")
        reviewViewModel.onReceiveCapturedImage(capturedImageList)

        var capturedList =ArrayList<CameraModel>()
        capturedList.add(CameraModel("abcdefgh",false))


        var cameraModelList = MutableLiveData<ArrayList<CameraModel>>()
        cameraModelList.value = capturedList
        assertEquals(capturedList.get(0).selected,reviewViewModel.capturedImageList.get(0).selected)

    }

    @Test
    fun receivedUploadImageId()
    {
        var id :String ="12@abc"
        reviewViewModel.receivedUploadImageId(id)

        assertEquals(null,reviewViewModel.imageUploadResponseId.value)
    }



}