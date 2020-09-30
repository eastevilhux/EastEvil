package com.good.framework.commons;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.god.uikit.entity.Image;
import com.good.framework.R;

import java.io.File;

public class ViewAttAdapter {
    private static final String TAG = "ViewAttAdapter==>";

    @BindingAdapter("android:src")
    public static void setImage(ImageView view, File file){
        if (view.getId() == R.id.iv_imageview) {
            Glide.with(view)
                    .load(file)
                    .apply(new RequestOptions()
                    .placeholder(R.drawable.icon_full_screen_nodata_img)
                    .error(R.drawable.icon_full_screen_nodata_img))
                    .into(view);
        }/*else if(view.getId() == R.id.iv_image){
            Log.d(TAG,"FILE=>");
                Glide.with(view)
                        .load(file)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_upload_image_default)
                                .error(R.drawable.ic_upload_image_default))
                        .into(view);
        }*/
    }

    @BindingAdapter("android:src")
    public static void setImage(ImageView view, Image image){
        if(view.getId() == R.id.iv_icon){
            RequestBuilder rb = null;
            if(image == null){
                rb = Glide.with(view).load(image.getResource());
            }else {
                switch (image.getType()) {
                    case TYPE_DEFAULT:
                        rb = Glide.with(view).load(image.getResource());
                        break;
                    case TYPE_RESOURCE:
                        rb = Glide.with(view).load(image.getResource());
                        break;
                    case TYPE_FILE:
                        rb = Glide.with(view).load(image.getFile());
                        break;
                    case TYPE_URL:
                        rb = Glide.with(view).load(image.getUrl());
                        break;
                    default:
                        rb = Glide.with(view).load(image.getResource());
                        break;
                }
            }
            RequestOptions rq = new RequestOptions()
                    .placeholder(R.drawable.ic_upload_image_default)
                    .error(R.drawable.ic_upload_image_default);
            rb.apply(rq)
                    .into(view);
        }
    }
}
