package com.omni.omnisdk.imageprocessingresult

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omni.omnisdk.R
import com.omni.omnisdk.databinding.ActivityImageProcessingBinding
import com.omni.omnisdk.utility.Constant

class ImageProcessingResultActivity : AppCompatActivity() {
    private lateinit var viewModel: ImageProcessingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityImageProcessingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_image_processing)
        viewModel = ViewModelProviders.of(this).get(ImageProcessingViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this


        if (intent.getStringExtra(Constant.EXTRA_ID) != null) {
            viewModel.getProcessingResults(intent.getStringExtra(Constant.EXTRA_ID))
        }

        viewModel.onReseivedProcessingResult.observe(this, Observer {
            val intent = Intent()
            intent.putExtra(Constant.RESPONSERESULT, it)
            setResult(Constant.PROCESSINGRESULTRESULTCODE, intent)
            finish()
        })


    }
}