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
        if(file == null)
            return;
        if (view.getId() == R.id.iv_imageview) {
            Log.d(TAG,file.getPath());
            Log.d(TAG,file.length()+"");
            Glide.with(view)
                    .load(file)
                    .apply(new RequestOptions()
                    .placeholder(R.drawable.icon_full_screen_nodata_img)
                    .error(R.drawable.icon_full_screen_nodata_img))
                    .into(view);
        }
    }
}
