package com.omni.omnisdk.utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Vibrator
import android.provider.MediaStore
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.omni.omnisdk.R
import com.omni.omnisdk.interfaces.PermissionGrantedCallBack
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object OmniUtil {
    var HEIGHT: Int = 0
    var WIDTH: Int = 0
    const val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 9921
    private fun addPermission(
        permissionsList: MutableList<String>,
        permission: String,
        ac: Activity
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ac.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission)
                // Check for Rationale Option
                return ac.shouldShowRequestPermissionRationale(permission)
            }
        }
        return true
    }


    fun checkForCamaraWritePermissions(
        activity: FragmentActivity,
        permissionGrantedCallBack: PermissionGrantedCallBack
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGrantedCallBack.permissionGranted(true)
        } else {
            val permissionsNeeded = ArrayList<String>()
            val permissionsList = ArrayList<String>()
            if (!addPermission(
                    permissionsList,
                    Manifest.permission.CAMERA,
                    activity
                )
            )
                permissionsNeeded.add("CAMERA")
            if (!addPermission(
                    permissionsList,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    activity
                )
            )
                permissionsNeeded.add("WRITE_EXTERNAL_STORAGE")
            if (permissionsList.size > 0) {
                activity.requestPermissions(
                    permissionsList.toTypedArray(),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                )
            } else {
                permissionGrantedCallBack.permissionGranted(true)
            }
        }
    }

    @JvmStatic
    fun writeImage(
        bitmap: Bitmap, path: String, quality: Int, newWidth: Int,
        newHeight: Int
    ): File {
        var bitmap = bitmap
        var newWidth = newWidth
        var newHeight = newHeight
        val dir = File(Environment.getExternalStorageDirectory(), path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val photo = File(
            dir, "IMG_"
                    + SimpleDateFormat("yyyyMMdd_HHmmSS", Locale.ENGLISH).format(Date())
                    + ".jpg"
        )
        if (photo.exists()) {
            photo.delete()
        }
        if (newWidth == 0 && newHeight == 0) {
            newWidth = bitmap.width / 2
            newHeight = bitmap.height / 2
        }
        bitmap = getResizedBitmap(bitmap, newWidth, newHeight)

        try {
            val fos = FileOutputStream(photo.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
            // fos.write(jpeg);
            fos.close()
        } catch (e: Exception) {
            Log.e("PictureDemo", "Exception in photoCallback", e)
        }

        return photo
    }

    @JvmStatic
    fun writeImageForAR(
        bitmap: Bitmap, path: String, quality: Int
    ): File {
        var bitmap = bitmap
        val dir = File(Environment.getExternalStorageDirectory(), path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val photo = File(
            dir, "IMG_"
                    + SimpleDateFormat("yyyyMMdd_HHmmSS", Locale.ENGLISH).format(Date())
                    + ".jpg"
        )
        if (photo.exists()) {
            photo.delete()
        }

        try {
            val fos = FileOutputStream(photo.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
            // fos.write(jpeg);
            fos.close()
        } catch (e: Exception) {
            Log.e("PictureDemo", "Exception in photoCallback", e)
        }

        return photo
    }

    private fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
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

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun showStatusBar(appCompatActivity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = appCompatActivity.window
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            w.statusBarColor = ContextCompat.getColor(appCompatActivity, R.color.colorStatusBar)
        }
    }

    fun getcurrentTime(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return sdf.format(Date())
//        return Calendar.getInstance().time.toString()
    }


    fun showStatusBarr(appCompatActivity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = appCompatActivity.window
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            w.statusBarColor = ContextCompat.getColor(appCompatActivity, R.color.colorStatusBar)
        }
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


    @JvmStatic
    fun rotateARImage(image: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
            matrix.postRotate(degree)
        // image.recycle()
        return Bitmap.createBitmap(
            image,
            0,
            0,
            image.width,
            image.height,
            matrix,
            false
        )
    }


    @JvmStatic
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
            val columnindex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnindex)
        } finally {
            cursor?.close()
        }
    }

    fun vibe(c: Context, l: Long) {
        (c.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(l)
    }

    fun getBase64String(capturedImage: String?): String {
        val selectedImage = BitmapFactory.decodeFile(capturedImage)
        val stream = ByteArrayOutputStream()
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, 0)
    }




}