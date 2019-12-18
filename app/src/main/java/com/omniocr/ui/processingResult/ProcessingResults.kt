package com.omniocr.ui.processingResult

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.omni.omnisdk.imageprocessingresult.ImageProcessingResultActivity
import com.omniocr.MainActivity
import com.omniocr.R
import com.omniocr.data.model.ProcessingResponseModel
import com.omniocr.util.Constant
import kotlinx.android.synthetic.main.activity_processing_results.*


class ProcessingResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing_results)

        val id = intent.getStringExtra(Constant.EXTRA_ID)
        if (id != null) {
            startActivityForResult(
                Intent(
                    this@ProcessingResults,
                    ImageProcessingResultActivity::class.java
                ).putExtra(Constant.EXTRA_ID, id), MainActivity.PROCESSINGRESULTRESULTCODE
            )
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            MainActivity.PROCESSINGRESULTRESULTCODE -> {
                if (data?.getStringExtra(Constant.RESPONSERESULT) != null) {
                    val response: ProcessingResponseModel = Gson().fromJson(
                        data?.getStringExtra(Constant.RESPONSERESULT),
                        ProcessingResponseModel::class.java
                    )

                    setValueToAdapter(response)
                }
            }
        }

    }

    private fun setValueToAdapter(response: ProcessingResponseModel) {
        rv_parent.adapter = ProcessingResultParentAdapter(response.result)
    }

}