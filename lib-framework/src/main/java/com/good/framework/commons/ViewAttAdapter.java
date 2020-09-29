package com.good.framework.commons;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
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
            switch (image.getType()){
                case TYPE_DEFAULT:
                    Glide.with(view)
                            .load(image.getResource())
                            .apply(new RequestOptions()
                            .error(R.drawable.ic_upload_image_default)
                                    .placeholder(R.drawable.ic_upload_image_default)
                            .into(view);
                    break;
                case TYPE_RESOURCE:
                case TYPE_FILE:
                case TYPE_URL:
                default:
                    break;
            }
        }
    }
}
