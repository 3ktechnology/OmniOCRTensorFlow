package com.omniocr.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Vibrator
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.omni.omnisdk.capture.Options
import com.omni.omnisdk.utility.ImageQuality
import com.omniocr.R
import com.omniocr.data.model.LoginModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object Utility {

    var HEIGHT: Int = 0
    var WIDTH: Int = 0
    private lateinit var options: Options


    fun setupStatusBarHidden(appCompatActivity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = appCompatActivity.window
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    fun showStatusBar(appCompatActivity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = appCompatActivity.window
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            w.statusBarColor = ContextCompat.getColor(appCompatActivity, R.color.colorStatusBar)
        }
    }


    fun hideStatusBar(appCompatActivity: AppCompatActivity) {
        synchronized(appCompatActivity) {
            val w = appCompatActivity.window
            val decorView = w.decorView
            // Hide Status Bar.
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
        }
    }

    fun getSoftButtonsBarSizePort(activity: Activity): Int {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val metrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            val usableHeight = metrics.heightPixels
            activity.windowManager.defaultDisplay.getRealMetrics(metrics)
            val realHeight = metrics.heightPixels
            return if (realHeight > usableHeight) {
                realHeight - usableHeight
            } else {
                0
            }
        }
        return 0
    }

    fun getScreenSize(activity: Activity) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        HEIGHT = displayMetrics.heightPixels
        WIDTH = displayMetrics.widthPixels
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun getDateDifference(context: Context, calendar: Calendar): String {
        val d = calendar.time
        val lastMonth = Calendar.getInstance()
        val lastWeek = Calendar.getInstance()
        val recent = Calendar.getInstance()
        lastMonth.add(Calendar.DAY_OF_MONTH, -Calendar.DAY_OF_MONTH)
        lastWeek.add(Calendar.DAY_OF_MONTH, -7)
        recent.add(Calendar.DAY_OF_MONTH, -2)
        return if (calendar.before(lastMonth)) {
            SimpleDateFormat("MMMM").format(d)
        } else if (calendar.after(lastMonth) && calendar.before(lastWeek)) {
            context.resources.getString(R.string.pix_last_month)
        } else if (calendar.after(lastWeek) && calendar.before(recent)) {
            context.resources.getString(R.string.pix_last_week)
        } else {
            context.resources.getString(R.string.pix_recent)
        }
    }


    fun vibe(c: Context, l: Long) {
        //  (c.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(l)
    }


    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
        return resizedBitmap.copy(Bitmap.Config.RGB_565, false)
    }


    fun rotate(scaledBitmap: Bitmap, i: Int): Bitmap {
        if (i == 0) {
            return scaledBitmap
        }
        val matrix = Matrix()
        matrix.preRotate((-i).toFloat())
        return Bitmap.createBitmap(
            scaledBitmap, 0, 0, scaledBitmap.width,
            scaledBitmap.height, matrix, false
        )
    }


    fun scanPhoto(pix: Context, photo: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val contentUri = Uri.fromFile(photo)
            scanIntent.data = contentUri
            pix.sendBroadcast(scanIntent)
        } else {
            pix.sendBroadcast(
                Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(photo.absolutePath))
            )
        }
    }


    fun isInternetConnected(c: Context): Boolean {
        val cm = c
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnectedOrConnecting
    }

    fun isValidEmail1(target: CharSequence?): Boolean {
        /*return if (target == null) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches()
        }*/


        return if (target == null) {
            false
        } else {
            val pattern: Pattern = Pattern.compile(".+@.+\\.[a-z]+")
            val matcher: Matcher = pattern.matcher(target)
            matcher.matches()
//            android.util.Patterns.EMAIL_ADDRESS.matcher(target)
//                .matches()
        }


    }


    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length < 5
    }

    fun isEmailPasswordValid(loginModel: LoginModel): Boolean? {
        val e = isValidEmail1(loginModel.getEmail())
        val p = !isValidPassword(loginModel.getPassword())
        return e && p
    }

    fun getcurrentTime(): String {
        var sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return sdf.format(Date())
//        return Calendar.getInstance().time.toString()
    }

    fun getCursor(context: Context): Cursor? {
        return context.contentResolver.query(
            Constant.URI,
            Constant.PROJECTION,
            null,
            null,
            Constant.ORDERBY
        )
    }

    fun getRealPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    fun getCameraOptions(context: Context): Options {
        options = Options.init()
            .setCount(20)
            .setFrontfacing(false)
            .setImageQuality(ImageQuality.HIGH)
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath(context.resources.getString(R.string.sdcard_path))

        return options
    }

}
