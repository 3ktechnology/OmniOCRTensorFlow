package com.omni.omnisdk.upload_images_to_server

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.google.gson.Gson
import com.omni.omnisdk.R
import com.omni.omnisdk.data.model.*
import com.omni.omnisdk.data.repository.NetworkClient
import com.omni.omnisdk.data.repository.NetworkInterface
import com.omni.omnisdk.utility.OmniUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class UploadImageViewModel(val app: Application) : AndroidViewModel(app) {
    private var credentials: BasicAWSCredentials? = null
    private var s3Client: AmazonS3Client? = null
    private lateinit var currentTime: String
    lateinit var folderName: String
    lateinit var bucketName: String
    /*private*/ var count: Int = 0
    private lateinit var formList: ArrayList<String>
    private var model = UploadImageMegaServerRequest()
    private var imageModel = ImageObject()
    private var rotationModel = RotationObject()
    private var request = UploadImagesUsingBase64Request()
    var uploadImageResponse = MutableLiveData<String>()




    fun uploadImageTOS3(frmList: ArrayList<String>?) {
        this.formList = frmList!!
        try {
            AWSMobileClient.getInstance().initialize(app).execute()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        credentials = BasicAWSCredentials(
            app.resources.getString(R.string.aws_key),
            app.resources.getString(R.string.aws_secret)
        )

        s3Client = AmazonS3Client(credentials)
        // s3Client!!.setRegion(Region.getRegion(Regions.US_EAST_1))
        currentTime = OmniUtil.getcurrentTime()
        folderName = app.resources.getString(R.string.aws_folder)
        bucketName = app.resources.getString(R.string.aws_folder)

        for (i in 0 until formList.size) {

            uploadFile(formList[i])
        }
    }

    /*private*/ fun uploadFile(capturedImage: String) {

        if (capturedImage != null) {

            val file = File(capturedImage)


            val transferUtility = TransferUtility.builder()
                .context(app)
                .awsConfiguration(AWSMobileClient.getInstance().configuration)
                .s3Client(s3Client)
                .build()

            /*val transferUtility = TransferUtility.builder()
                .context(app)
                .s3Client(s3Client)
                .build()*/

            val uploadObserver =
                transferUtility.upload("$folderName/$currentTime.png", file)

            uploadObserver.setTransferListener(object : TransferListener {

                override fun onStateChanged(id: Int, state: TransferState) {
                    if (TransferState.COMPLETED == state) {
                        count++
                        Toast.makeText(app, "Upload Completed- $count", Toast.LENGTH_SHORT)
                            .show()
                        if (count == formList.size) {
                            Log.i("count ::", count.toString())
                            Log.i("formList.size ::", count.toString())
                            Toast.makeText(app, "All Upload Completed.", Toast.LENGTH_SHORT)
                                .show()
                            uploadImagesToMegaServer()
                        }
                        // downloadImage(transferUtility, "$folderName/$currentTime.png", file)


                        file.delete()
                    } else if (TransferState.FAILED == state) {
                        file.delete()
                    }
                }

                override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                    val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                    val percentDone = percentDonef.toInt()

                    // tvFileName.setText("ID:$id|bytesCurrent: $bytesCurrent|bytesTotal: $bytesTotal|$percentDone%")
                }

                override fun onError(id: Int, ex: Exception) {
                    ex.printStackTrace()
                }

            })
        }

    }

    fun onStateChange(id: Int, state: TransferState) {
       var capturedImage= "/storage/emulated/0/DCIM/Camera/IMG_20191101_145515715.jpg"
        var file =File(capturedImage)
        var formListdummy= ArrayList<String>()
        formListdummy.add(capturedImage)
        if (TransferState.COMPLETED == state) {
            count++
//            Toast.makeText(app, "Upload Completed- $count", Toast.LENGTH_SHORT)
//                .show()
            if (count == formListdummy.size) {
//                Log.i("count ::", count.toString())
//                Log.i("formList.size ::", count.toString())
//                Toast.makeText(app, "All Upload Completed.", Toast.LENGTH_SHORT)
//                    .show()
//                uploadImagesToMegaServer()
            }
            // downloadImage(transferUtility, "$folderName/$currentTime.png", file)


            file.delete()
        } else if (TransferState.FAILED == state) {
            file.delete()
        }
    }

    /*private*/ fun uploadImagesToMegaServer() {
        model.endStep = "ocr"
        model.startStep = "segment"
        imageModel.s3Bucket = "omnibucket"
        imageModel.s3Key = "AKIA2343P5N4NW4O6R46"
        model.image = imageModel
        rotationModel.rotation = -0.3
        model.rotation = rotationModel
        uploadImageObservable.subscribeWith(uploadImageObserver)

    }

    fun uploadImageUsingBase64(formList: ArrayList<String>?) {
        val base64ImageString = OmniUtil.getBase64String(formList?.get(0))
        var rotation = RotationModel()
        var imageBase64 = ImageModel()
        rotation.rotation = -0.3
        imageBase64.image_base64 = base64ImageString

        request.start_step = app.resources.getString(R.string.start_step)
        request.end_step = app.resources.getString(R.string.end_step)
        request.rotation = rotation
        request.image = imageBase64
        uploadImageBase64Observable.subscribeWith(uploadImageBase64Observer)
    }


    private val uploadImageBase64Observable: Observable<UploadImageMegaServerResponse>
        get() = NetworkClient.getRetrofit()!!.create(NetworkInterface::class.java)
            .uploadImageBase64("application/json", request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private val uploadImageBase64Observer: DisposableObserver<UploadImageMegaServerResponse>
        get() = object : DisposableObserver<UploadImageMegaServerResponse>() {
            override fun onNext(@NonNull response: UploadImageMegaServerResponse) {
                Log.i("response:::", response.toString())

                uploadImageResponse.value = Gson().toJson(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                //  loginView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                // loginView!!.hideProgress()
            }
        }


    private val uploadImageObservable: Observable<UploadImageMegaServerResponse>
        get() = NetworkClient.getRetrofit()!!.create(NetworkInterface::class.java)
            .uploadImage("application/json", model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private val uploadImageObserver: DisposableObserver<UploadImageMegaServerResponse>
        get() = object : DisposableObserver<UploadImageMegaServerResponse>() {
            override fun onNext(@NonNull response: UploadImageMegaServerResponse) {
                Log.i("response:::", response.toString())
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                //  loginView!!.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                // loginView!!.hideProgress()
            }
        }




}