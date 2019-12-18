package com.omniocr.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omni.omnisdk.interfaces.PermissionGrantedCallBack
import com.omni.omnisdk.utility.OmniUtil
import com.omniocr.R
import com.omniocr.databinding.FragmentHomeBinding
import com.omniocr.ui.review.ReviewImageListActivity
import com.omniocr.util.HostPreference

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var imageList = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel =
            activity?.let { ViewModelProviders.of(it).get(HomeViewModel::class.java) }!!
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HostPreference.setBoolean(com.omniocr.util.Constant.RECAPTURE, false)
        homeViewModel.onAssesmentClick.observe(this, Observer {
            if (it) {
                OmniUtil.checkForCamaraWritePermissions(activity!!,
                    object : PermissionGrantedCallBack {
                        override fun permissionGranted(isGranted: Boolean) {

                            homeViewModel.permissionDranted()
                            //captureImage()
                        }
                    })

            }
        })

        homeViewModel.imageList.observe(this, Observer {
            imageList.clear()
            imageList.addAll(it)
            startActivity(
                Intent(activity, ReviewImageListActivity::class.java)
                    .putExtra("imageList", imageList)
            )
        })
    }

    private fun captureImage() {


        /*CaptureImage(object : CapturedSelectedImageList {
            override fun onCapturedOrSelected(imageList: ArrayList<Uri>) {
                for (i in 0 until imageList.size) {
                    Log.i("list", imageList[i].toString())
                }
            }
        })*/
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            OmniUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // CaptureImage.start(activity!!, options)

                    captureImage()

                } else {
                    Toast.makeText(
                        activity,
                        "Approve permissions to open Omni ImagePicker",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }


/*
    override fun onCapturedOrSelected(imageList: ArrayList<Uri>) {
        for (i in 0 until imageList.size) {
            Log.i("list", imageList[i].toString())
        }

    }*/
}