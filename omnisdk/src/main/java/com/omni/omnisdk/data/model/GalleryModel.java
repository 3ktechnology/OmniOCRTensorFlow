package com.omni.omnisdk.data.model;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class GalleryModel {
    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Uri image;
    public Boolean selected;
    public GalleryModel(Uri image, Boolean selected) {
        this.image = image;
        this.selected = selected;
    }

    @BindingAdapter({"bind:image"})
    public static void loadImage(ImageView view, Uri imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }


}
