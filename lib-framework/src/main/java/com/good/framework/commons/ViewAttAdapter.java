package com.good.framework.commons;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        }else if(view.getId() == R.id.iv_image){
            Log.d(TAG,"FILE=>");
                Glide.with(view)
                        .load(file)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_upload_image_default)
                                .error(R.drawable.ic_upload_image_default))
                        .into(view);
        }
    }
}
