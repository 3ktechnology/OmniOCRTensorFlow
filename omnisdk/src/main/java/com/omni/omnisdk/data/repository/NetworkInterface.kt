package com.omni.omnisdk.data.repository


import com.omni.omnisdk.data.model.OmniProcessingResultModel
import com.omni.omnisdk.data.model.UploadImageMegaServerRequest
import com.omni.omnisdk.data.model.UploadImageMegaServerResponse
import com.omni.omnisdk.data.model.UploadImagesUsingBase64Request
import io.reactivex.Observable
import retrofit2.http.*


interface NetworkInterface {

    @Headers("x-api-key:INSECURE-6")
    @POST("pipeline")
    fun uploadImage(
        @Header("Content-Type") content_type: String,
        @Body imageRequest: UploadImageMegaServerRequest
    ): Observable<UploadImageMegaServerResponse>

    @Headers("x-api-key:INSECURE-6")
    @POST("pipeline")
    fun uploadImageBase64(
        @Header("Content-Type") content_type: String,
        @Body imageRequest: UploadImagesUsingBase64Request
    ): Observable<UploadImageMegaServerResponse>

    @Headers("x-api-key:INSECURE-6")
    @GET("pipeline/{id}")
    fun getProcessingResult(
        @Header("Content-Type") content_type: String,
        @Path("id") id: String
    ): Observable<OmniProcessingResultModel>


}
