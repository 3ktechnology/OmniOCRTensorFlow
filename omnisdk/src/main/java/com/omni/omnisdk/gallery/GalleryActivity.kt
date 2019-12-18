package com.omni.omnisdk.gallery

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.omni.omnisdk.R
import com.omni.omnisdk.capture.CaptureImage
import com.omni.omnisdk.data.model.GalleryModel
import com.omni.omnisdk.databinding.ActivityGaleryBinding
import com.omni.omnisdk.utility.ImageFetcher
import com.omni.omnisdk.utility.OmniUtil
import com.omni.omnisdk.utility.OmniUtil.vibe
import kotlinx.android.synthetic.main.activity_galery.*

class GalleryActivity : AppCompatActivity() {
    lateinit var viewModel: GalleryViewModel
    lateinit var glleryAdapter: GalleryAdapter
    private val imageList = ArrayList<GalleryModel>()
    private var cameraImageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityGaleryBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_galery)

        viewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        cameraImageList = intent.getStringArrayListExtra("list")

        rv.layoutManager = GridLayoutManager(this, 3)

        glleryAdapter = GalleryAdapter(this@GalleryActivity, object : GalleryAdapter.OnItemClicked {
            override fun onClick(pos: Int) {
                viewModel.updateClick(pos, imageList)
            }

        }, object : GalleryAdapter.OnLongItemClicked {
            override fun onLongClick(pos: Int) {
                vibe(this@GalleryActivity, 50)
                viewModel.updateLongClick(pos, imageList)

            }
        })
        rv.adapter = glleryAdapter

        val imageFetcher = @SuppressLint("StaticFieldLeak")
        object : ImageFetcher(applicationContext) {
            override fun onPostExecute(imgs: ModelList) {
                super.onPostExecute(imgs)
                imageList.addAll(imgs.list)
                glleryAdapter.addImageList(imgs.list)

            }
        }

        imageFetcher.execute(OmniUtil.getCursor(this@GalleryActivity))



        viewModel.singleClickedImage.observe(this, Observer {

            val resultIntent = Intent()
            resultIntent.putStringArrayListExtra(CaptureImage.IMAGE_RESULTS, it)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        })

        viewModel.isNotifyAdapter.observe(this, Observer {
            if (it) {
                glleryAdapter.notifyDataSetChanged()
            }
        })


        viewModel.imageCountReached.observe(this, Observer {
            OmniUtil.showToast(
                this@GalleryActivity, String.format(
                    resources.getString(R.string.cannot_click_image_pix),
                    "" + it
                )
            )
        })

    }
}
