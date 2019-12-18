package com.omniocr.ui.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omniocr.BR
import com.omniocr.R
import com.omniocr.data.model.CameraModel
import com.omniocr.databinding.ReviewListItemBinding


class ReviewImagesAdapter(
    private val imageList: ArrayList<CameraModel>,
    val onClick: OnFormItemClick
) : RecyclerView.Adapter<ReviewImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ReviewListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.review_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cameraModel = imageList[position]
        holder.bind(cameraModel)
        holder.itemView.setOnClickListener {
            onClick.itemPos(position)
        }
    }


    override fun getItemCount(): Int {
        return imageList.size
    }

    class ViewHolder(private val itemBinding: ReviewListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(obj: Any) {
            itemBinding.setVariable(BR.cameramodel, obj)
            itemBinding.executePendingBindings()
        }

    }

    interface OnFormItemClick {
        fun itemPos(pos: Int)
    }
}

