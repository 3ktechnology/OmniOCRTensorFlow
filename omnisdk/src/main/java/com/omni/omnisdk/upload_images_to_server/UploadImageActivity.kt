package com.omni.omnisdk.upload_images_to_server

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.omni.omnisdk.R
import com.omni.omnisdk.databinding.ActivityUploadBinding
import com.omni.omnisdk.utility.Constant


class UploadImageActivity : AppCompatActivity() {
    private lateinit var viewModel: UploadImageViewModel
    private var s3Client: AmazonS3Client? = null
    private var credentials: BasicAWSCredentials? = null
    private lateinit var currentTime: String
    lateinit var folderName: String
    private lateinit var formList: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityUploadBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_upload)
        viewModel = ViewModelProviders.of(this).get(UploadImageViewModel::class.java)
        binding.viewmodel = viewModel

        formList = intent.getStringArrayListExtra(Constant.EXTRA_ID)

        // viewModel.uploadImageTOS3(formList)
        viewModel.uploadImageUsingBase64(formList)

        viewModel.uploadImageResponse.observe(this, Observer {
            val intent = Intent()
            intent.putExtra(Constant.EXTRA_ID, it)
            setResult(Constant.UPLOADIMAGERESULTCODE, intent)
            finish()
        })
    }


}