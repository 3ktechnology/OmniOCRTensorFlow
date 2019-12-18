package com.omni.omnisdk.gallery

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.omni.omnisdk.data.model.GalleryModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GalleryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var media: MediaStore.Images.Media

    @Mock
    lateinit var uri: Uri

    @Mock
    lateinit var view: View


    lateinit var galleryViewModel: GalleryViewModel

    @Before
    fun setUp() {
        application = Mockito.mock(Application::class.java)
        galleryViewModel = GalleryViewModel(application)
        media = Mockito.mock(MediaStore.Images.Media::class.java)
        uri = Mockito.mock(Uri::class.java)
        view = Mockito.mock(View::class.java)

    }


      @Test(expected = NullPointerException::class)
      fun updateClick() {

  //        uri = Uri.parse("file://mnt/sdcard/temp_image0.png")

  //        uri = Uri.parse("content://media/external/images/media/133222")

          var galleryModel =
              GalleryModel(uri, true)
          var list =ArrayList<GalleryModel>()
          list.add(galleryModel)

          galleryViewModel.updateClick(0,list)
      }


    @Test
    fun updateLongClick() {


        var galleryModel = GalleryModel(uri, true)
        var list = ArrayList<GalleryModel>()
        list.add(galleryModel)

        galleryViewModel.updateLongClick(0, list)
        assertEquals(true, galleryViewModel.isNotifyAdapter.value)
        assertEquals(true, galleryViewModel.toolbarVisibility.value)

    }


    @Test
    fun onOkClicked() {
        galleryViewModel.onOkClicked(view)

    }

    @Test(expected = NullPointerException::class)
    fun getRealPathFromUri() {

        var galleryModel =
            GalleryModel(uri, true)
        var list =ArrayList<GalleryModel>()
        list.add(galleryModel)

        galleryViewModel.getRealPathFromUri(list)
    }
}