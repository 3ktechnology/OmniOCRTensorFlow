package com.omniocr.data.model

import com.google.gson.annotations.SerializedName

class ProcessingResponseModel {
    @SerializedName("request_status")
    var request_status: RequestStatus? = null
    @SerializedName("result")
    var result: ProcessingResult? = null
    @SerializedName("support_info")
    var support_info: ProcessingSupportInfo? = null
}

class ProcessingSupportInfo {
    @SerializedName("internal_trace_id")
    var internal_trace_id: String? = null
}

class RequestStatus {

    @SerializedName("final")
    var final: Boolean? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("ready")
    var ready: Boolean? = null
    @SerializedName("state")
    var state: String? = null

}

class ProcessingResult {
    @SerializedName("issue_extraction")
    var issue_extraction: IssueExtracted? = null
    @SerializedName("predicted_key_values")
    val predicted_key_values: HashMap<String, ArrayList<PredictedKeyValue>>? = null
    @SerializedName("predicted_rotation")
    var predicted_rotation: PredictedRotation? = null
    @SerializedName("predicted_segments")
    var predicted_segments: ArrayList<PredictedSegmentData>? = null
    @SerializedName("predicted_text_boxes")
    var predicted_text_boxes: ArrayList<PredictedTextBoxData>? = null
    @SerializedName("predicted_value_boxes")
    var predicted_value_boxes: ArrayList<PredictedValueBoxData>? = null

}

class PredictedSegmentData {
    @SerializedName("Type")
    var type: String? = null
    @SerializedName("box")
    var box: ArrayList<Int>? = null
}


class PredictedTextBoxData {
    @SerializedName("Type")
    var type: String? = null
    @SerializedName("box")
    var box: ArrayList<Int>? = null
    @SerializedName("confidence")
    var confidence: Number? = null
    @SerializedName("text")
    var text: String? = null
}

class PredictedValueBoxData {
    @SerializedName("Type")
    var type: String? = null
    @SerializedName("box")
    var box: ArrayList<Int>? = null
    @SerializedName("box_id")
    var box_id: Int? = null
    @SerializedName("cleaned_text")
    var cleaned_text: String? = null
    @SerializedName("confidence")
    var confidence: Number? = null
    @SerializedName("is_value")
    var is_value: Boolean? = null
    @SerializedName("key")
    var key: String? = null
    @SerializedName("key_confidence")
    var key_confidence: Number? = null
    @SerializedName("text")
    var text: String? = null
    @SerializedName("value_num")
    var value_num: Int? = null
    @SerializedName("value_num_confidence")
    var value_num_confidence: Number? = null
}


class IssueExtracted {
    @SerializedName("date_found")
    var date_found: ArrayList<IssueExtractedDayFound>? = null
    @SerializedName("family_history")
    var family_history: FamilyHistory? = null
    @SerializedName("icd10_codes")
    var icd10_codes: IcdCodes? = null
    @SerializedName("lab_results")
    var lab_results: LabResult? = null
    @SerializedName("medical_terms")
    var medical_terms: MedicalTerms? = null
    @SerializedName("medications")
    var medications: Medications? = null
    @SerializedName("physical_measurements")
    val physical_measurements: HashMap<String, ArrayList<PhysicalMeasurement>>? = null
    @SerializedName("pii")
    var pii: Pii? = null
}

class FamilyHistory {
    @SerializedName("family_history")
    var family_history: ArrayList<FamilyHistoryList>? = null
    @SerializedName("family_members")
    var family_members: FamilyMembers? = null
}

class FamilyHistoryList {

}

class FamilyMembers {

}

class IcdCodes {

}

class LabResult {

}

class MedicalTerms {

}

class Medications {
    @SerializedName("medication_pages")
    var medication_pages: MedicationPages? = null
    @SerializedName("medication_sections")
    var medication_sections: ArrayList<MedicationSection>? = null

}

class MedicationPages {

}

class MedicationSection {

}

class PhysicalMeasurement {
    @SerializedName("box")
    var box: ArrayList<Int>? = null
    @SerializedName("text")
    var text: String? = null
}



class Pii {

}


class IssueExtractedDayFound {

}

class PredictedKeyValue {
    @SerializedName("box_id")
    var box_id: Int? = null
    @SerializedName("isAbnormal")
    var isAbnormal: Boolean? = null
    @SerializedName("key_confidence")
    var key_confidence: Number? = null
    @SerializedName("num")
    var num: Number? = null
    @SerializedName("text")
    var text: String? = null
    @SerializedName("text_confidence")
    var text_confidence: Number? = null
    @SerializedName("value_num_confidence")
    var value_num_confidence: Number? = null
}

class PredictedRotation {
    @SerializedName("confidence")
    var confidence: Number? = null
    @SerializedName("rotation")
    var rotation: Number? = null
}

