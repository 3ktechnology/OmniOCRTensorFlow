package com.omni.omnisdk.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.omni.omnisdk.BR
import com.omni.omnisdk.R
import com.omni.omnisdk.data.model.GalleryModel
import com.omni.omnisdk.databinding.GalleryRowItemBinding


class GalleryAdapter(
    val context: Context,
    private val listener: OnItemClicked,
    private val longLlistener: OnLongItemClicked
) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    var list = ArrayList<GalleryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.gallery_row_item, parent, false
        )

        return ViewHolder(binding as GalleryRowItemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = list[position]
        /*glide.load(image.image).into(holder.preview)
        */

        holder.itemView.setOnLongClickListener {
            longLlistener.onLongClick(position)
            true
        }
        holder.itemView.setOnClickListener {
            listener.onClick(position)
        }

        holder.bind(image)

    }

    fun addImageList(list: ArrayList<GalleryModel>?) {

        if (list != null) {
            this.list = list
            notifyDataSetChanged()
        }
    }

    class ViewHolder(private var itemRowBinding: GalleryRowItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        // val preview = itemRowBinding.imageView

        fun bind(obj: Any) {
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
        }
    }

    interface OnItemClicked {
        fun onClick(pos: Int)
    }

    interface OnLongItemClicked {
        fun onLongClick(pos: Int)
    }


}