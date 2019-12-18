package com.omniocr

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omni.omnisdk.capture.CaptureImage
import com.omniocr.ui.home.HomeFragment
import com.omniocr.ui.home.HomeViewModel
import com.omniocr.util.Constant
import com.omniocr.util.Utility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        Image_view_menu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    drawer_layout.closeDrawer(Gravity.LEFT)
                    switchFragment(HomeFragment())
                }
            }
            return@setNavigationItemSelectedListener true
        }

        val headerView = nav_view.getHeaderView(0)
        val buildNymber = headerView.findViewById(R.id.textView) as TextView
        buildNymber.text = "Version : " + BuildConfig.VERSION_NAME

        viewModel.nextActivityTocapture.observe(this, Observer {

            startActivityForResult(
                Intent(this@MainActivity, CaptureImage::class.java)
                    .putExtra("options", Utility.getCameraOptions(applicationContext)),
                IMAGERESULTCODE
            )
        })

    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGERESULTCODE && data?.getStringArrayListExtra(Constant.EXTRA_ID) != null) {
            viewModel.onReceiveCapturedImage(data?.getStringArrayListExtra(Constant.EXTRA_ID))
        }
    }

    companion object {
        const val IMAGERESULTCODE = 100
        const val UPLOADIMAGERESULTCODE = 101
        const val PROCESSINGRESULTRESULTCODE = 102
    }

}
