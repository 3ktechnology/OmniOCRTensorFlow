package com.omniocr.omniocr.home

import android.app.Application
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.omniocr.ui.home.HomeViewModel
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule =InstantTaskExecutorRule()

    @Mock
    lateinit var application:Application

    @InjectMocks
    lateinit var homeViewModel : HomeViewModel

    @Mock
    lateinit var view : View

    @Before
    fun setUp() {
        application = Mockito.mock(Application::class.java)
        view = Mockito.mock(View::class.java)
        homeViewModel = HomeViewModel(application)
    }

    @Test
    fun onAssessmentClicked() {
        homeViewModel.onAssessmentClicked(view)
        assertEquals(true,homeViewModel.onAssesmentClick.value)
    }

    @Test
    fun permissionDranted() {
        homeViewModel.permissionDranted()
        assertEquals(true,homeViewModel.nextActivityTocapture.value)
    }

    @Test
    fun onReceiveCapturedImage() {
        var capturedList = MutableLiveData<ArrayList<String>>()
        var capturedImageList=  ArrayList<String>()
        capturedImageList.add("/storage/emulated/0/Pictures/Screenshots/Screenshot_20191126-111051.png")
        capturedList.value= capturedImageList
        homeViewModel.onReceiveCapturedImage(capturedImageList)
        assertEquals(capturedList.value,homeViewModel.imageList.value)

    }
}