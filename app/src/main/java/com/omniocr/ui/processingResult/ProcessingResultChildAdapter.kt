package com.omniocr.ui.processingResult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.omniocr.BR
import com.omniocr.R
import com.omniocr.data.model.PredictedTextBoxData
import com.omniocr.databinding.ChildProcessingResultItemBinding

class ProcessingResultChildAdapter(
    private val list: ArrayList<PredictedTextBoxData>
) :
    RecyclerView.Adapter<ProcessingResultChildAdapter.ViewHolder>() {
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {


        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.child_processing_result_item, parent, false
        )

        return ViewHolder(binding as ChildProcessingResultItemBinding)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val imageModelList = list[position]
        holder.bind(imageModelList)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private var itemRowBinding: ChildProcessingResultItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {

        fun bind(obj: Any) {
            itemRowBinding.setVariable(BR.childmodel, obj)
            itemRowBinding.executePendingBindings()
        }
    }

}
