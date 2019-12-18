package com.omni.omnisdk.imageprocessingresult

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.omni.omnisdk.data.model.OmniProcessingResultModel
import com.omni.omnisdk.data.repository.NetworkClient
import com.omni.omnisdk.data.repository.NetworkInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ImageProcessingViewModel : ViewModel() {

    //processingResult
    var onReseivedProcessingResult = MutableLiveData<String>()
    private lateinit var processingResultId: String
    var responseReceived = MutableLiveData<Boolean>()
    var apiCount = 0
    var apiRequestCount = MutableLiveData<Int>()

    fun getProcessingResults(id: String?) {
        if (id != null) {
            processingResultId = id
        }
        processingResultObservable.subscribeWith(processingResultObserver)
    }

    private val processingResultObservable: Observable<OmniProcessingResultModel>
        get() = NetworkClient.getRetrofit()!!.create(NetworkInterface::class.java)
            .getProcessingResult(
                "application/json", processingResultId

            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    private val processingResultObserver: DisposableObserver<OmniProcessingResultModel>
        get() = object : DisposableObserver<OmniProcessingResultModel>() {
            override fun onNext(@NonNull response: OmniProcessingResultModel) {
                Log.i("response:::", response.toString())
                if (response.result == null) {
                    apiCount += 1
                    responseReceived.value = false
                    apiRequestCount.value = apiCount
                    processingResultObservable.subscribeWith(processingResultObserver)

                } else {
                    responseReceived.value = true
                    onReseivedProcessingResult.value = Gson().toJson(response)
                }


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