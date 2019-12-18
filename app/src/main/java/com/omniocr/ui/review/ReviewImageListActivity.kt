package com.omniocr.ui.review

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.omni.omnisdk.capture.CaptureImage
import com.omni.omnisdk.upload_images_to_server.UploadImageActivity
import com.omniocr.MainActivity
import com.omniocr.R
import com.omniocr.data.model.CameraModel
import com.omniocr.databinding.ActivityReviewImageBinding
import com.omniocr.ui.processingResult.ProcessingResults
import com.omniocr.util.Constant
import com.omniocr.util.HostPreference
import com.omniocr.util.Utility
import kotlinx.android.synthetic.main.activity_review_image.*


class ReviewImageListActivity : AppCompatActivity() {

    lateinit var reviewViewModel: ReviewViewModel
    var capturedImageList = ArrayList<CameraModel>()
    lateinit var adaptr: ReviewImagesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityReviewImageBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_review_image)
        binding.lifecycleOwner = this
        reviewViewModel =
            ViewModelProviders.of(this).get(ReviewViewModel::class.java)
        binding.viewModel = reviewViewModel

        HostPreference.setBoolean(Constant.RECAPTURE, false)

        getIntentData()

        reviewViewModel.isSelectAllClicked.observe(this, Observer {
            if (it) {
                for (i in 0 until capturedImageList.size) {
                    capturedImageList[i].selected = true
                }

            } else {
                for (i in 0 until capturedImageList.size) {
                    capturedImageList[i].selected = false
                }
            }
            adaptr.notifyDataSetChanged()

            getAllSelectedCount(capturedImageList)
        })

        reviewViewModel.updateImageList.observe(this, Observer {
            capturedImageList = it

            page_count.text = "Review (" + capturedImageList.size + ")"

            setValueToAdapter()
        })

        reviewViewModel.isDeleteClicked.observe(this, Observer {
            if (it) {
                reviewViewModel.deleteFormFromList(capturedImageList)
            }
        })

        reviewViewModel.isReCaptureClicked.observe(this, Observer {
            if (it) {
                startActivityForResult(
                    Intent(this@ReviewImageListActivity, CaptureImage::class.java)
                        .putExtra("options", Utility.getCameraOptions(applicationContext)),
                    MainActivity.IMAGERESULTCODE
                )
            }
        })

        reviewViewModel.formDeletePerformed.observe(this, Observer {
            adaptr.notifyDataSetChanged()
            page_count.text = "Selected (0/" + capturedImageList.size + " Pages)"
            HostPreference.setBoolean(Constant.DELETE_CLICKED, true)

        })

        reviewViewModel.uploadImageToMegaServer.observe(this, Observer {
            if (it) {
                var imageStringList = ArrayList<String>()
                for (i in 0 until capturedImageList.size) {
                    imageStringList.add(capturedImageList[i].capturedImage)
                }
                startActivityForResult(
                    Intent(
                        this@ReviewImageListActivity,
                        UploadImageActivity::class.java
                    ).putExtra(Constant.EXTRA_ID, imageStringList),
                    MainActivity.UPLOADIMAGERESULTCODE
                )
                capturedImageList.clear()
            }
        })

        reviewViewModel.isSelectClicked.observe(this, Observer {
            if (it) {
                page_count.text = "Selected (0/" + capturedImageList.size + " Pages)"
            }
        })

        reviewViewModel.isSelectEnabled.observe(this, Observer {
            if (it) {
                getAllSelectedCount(capturedImageList)
            } else {
                page_count.text = "Review (" + capturedImageList.size + ")"
            }
        })

        reviewViewModel.imageUploadResponseId.observe(this, Observer {
            startActivity(
                Intent(this@ReviewImageListActivity, ProcessingResults::class.java)
                    .putExtra(Constant.EXTRA_ID, it)
            )
            finish()

        })

        reviewViewModel.isBackClicked.observe(this, Observer {
            if (it) {
                finish()
            }
        })

        val wordtoSpan =
            SpannableString(resources.getString(R.string.title_no_image_available))

        wordtoSpan.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.colorGreen)),
            27,
            37,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        text1.text = wordtoSpan

        text1.setOnClickListener {
            startActivityForResult(
                Intent(this@ReviewImageListActivity, CaptureImage::class.java)
                    .putExtra("options", Utility.getCameraOptions(applicationContext)),
                MainActivity.IMAGERESULTCODE
            )
        }
    }

    private fun setValueToAdapter() {
        image_list_rv.layoutManager = GridLayoutManager(this, 3)
        adaptr =
            ReviewImagesAdapter(
                capturedImageList,
                object :
                    ReviewImagesAdapter.OnFormItemClick {
                    override fun itemPos(pos: Int) {
                        capturedImageList[pos].selected = !capturedImageList[pos].selected
                        adaptr.notifyDataSetChanged()
                        reviewViewModel.enableSelect(capturedImageList)
                    }

                })
        image_list_rv.adapter = adaptr
    }


    private fun getIntentData() {
        reviewViewModel.getImageListFromIntent(intent.getStringArrayListExtra("imageList"))
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun getAllSelectedCount(capturedImageList: ArrayList<CameraModel>) {
        var count = 0
        for (i in 0 until capturedImageList.size) {
            if (capturedImageList[i].selected) {
                count++
            }
        }
        page_count.text = "Selected (" + count + "/" + capturedImageList.size + " Pages)"

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            MainActivity.IMAGERESULTCODE -> {
                if (data?.getStringArrayListExtra(Constant.EXTRA_ID) != null) {
                    reviewViewModel.onReceiveCapturedImage(data?.getStringArrayListExtra(Constant.EXTRA_ID))
                    reStartActivity()
                }
            }
            MainActivity.UPLOADIMAGERESULTCODE -> {
                if (data?.getStringExtra(Constant.EXTRA_ID) != null) {
                    reviewViewModel.receivedUploadImageId(data?.getStringExtra(Constant.EXTRA_ID))
                }
            }
            MainActivity.PROCESSINGRESULTRESULTCODE -> {

            }


        }

    }

    private fun reStartActivity() {
        finish()
        startActivity(intent)
    }

}