package com.omniocr.data.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CameraModel {

    public String capturedImage;
    public Boolean selected;


    public CameraModel(String capturedImage, Boolean selected) {
        this.capturedImage = capturedImage;
        this.selected = selected;
    }


    public CameraModel() {
    }


    @BindingAdapter("capturedImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().centerInside())
                .into(view);
    }


}
