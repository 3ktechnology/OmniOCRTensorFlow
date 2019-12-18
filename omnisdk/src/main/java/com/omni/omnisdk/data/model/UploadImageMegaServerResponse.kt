package com.omni.omnisdk.data.model

import com.google.gson.annotations.SerializedName

class UploadImageMegaServerResponse {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("support_info")
    var supportInfo: SupportInfo? = null
}

class SupportInfo {
    @SerializedName("internal_trace_id")
    var internalTraceId: String? = null
}