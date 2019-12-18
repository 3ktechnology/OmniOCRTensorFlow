package com.omniocr.ui.processingResult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.omniocr.R
import com.omniocr.data.model.ProcessingResult


class ProcessingResultParentAdapter(
    private val processingResult: ProcessingResult?
) :
    RecyclerView.Adapter<ProcessingResultParentAdapter.ViewHolder>() {
    private var pos = 0

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {


        /*val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.parent_processing_result_item, parent, false
        )

        return ViewHolder(binding as ParentProcessingResultItemBinding)*/

        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.parent_processing_result_item, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        var predectedValue: String = ""
        var pos = 0
        var keySet = processingResult?.predicted_key_values?.entries
        if (keySet != null) {
            for (key in keySet) {
                if (pos == position) {
                    holder.text1.text = key.key
                    for (i in 0 until key.value!!.size) {
                        predectedValue = if (predectedValue == "") {
                            key.value[i].text.toString()
                        } else {
                            predectedValue + "   " + key.value[i].text
                        }

                    }

                    holder.text2.text = predectedValue
                }
                pos++
            }
        }

    }

    override fun getItemCount(): Int {
        return processingResult?.predicted_key_values!!.size
    }

    /*inner class ViewHolder(private var itemRowBinding: ParentProcessingResultItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {

        fun bind(
            obj: Any,
            imageShareModelList: ArrayList<PredictedTextBoxData>?
        ) {
            itemRowBinding.setVariable(BR.model, obj)
            val nestedAdapter = ProcessingResultChildAdapter(imageShareModelList!!)
            itemRowBinding.setVariable(BR.adapter, nestedAdapter)
            itemRowBinding.executePendingBindings()
        }
    }*/

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1: TextView = itemView.findViewById(R.id.text1)
        val text2: TextView = itemView.findViewById(R.id.text2)
        val text3: TextView = itemView.findViewById(R.id.text3)
    }

}