package com.god.uikit.commons

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.god.uikit.R
import java.io.File

class ViewAttAdapter {

    companion object{
        private const val TAG = "UIKIT_ViewAttAdapter==>"

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textView: TextView, text: Int) {
            when(textView.id){
                else->textView.setText(text);
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView,file:File?){
            Log.d(TAG,"WTF");
            when(imageView.id){
                R.id.iv_img_file->{
                    if(file == null){
                        imageView.setImageResource(R.drawable.ic_upload_image_default)
                    }else{
                        Glide.with(imageView)
                            .load(file)
                            .apply(RequestOptions()
                                .placeholder(R.drawable.ic_upload_image_default)
                                .error(R.drawable.ic_upload_image_default))
                            .into(imageView)
                    }
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView?,url:String?){
            when(imageView?.id){
                R.id.iv_url->{
                    Glide.with(imageView)
                        .load(url)
                        .apply(RequestOptions()
                            .placeholder(R.drawable.ic_upload_image_default)
                            .error(R.drawable.ic_upload_image_default))
                        .into(imageView);
                }
            }
        }


        @JvmStatic
        @BindingAdapter("android:src")
        fun setUikitImage(imageView: ImageView?,resource:Int?){
            when(imageView?.id){
                R.id.iv_resource->{
                    Glide.with(imageView)
                        .load(resource)
                        .apply(RequestOptions()
                            .placeholder(R.drawable.ic_upload_image_default)
                            .error(R.drawable.ic_upload_image_default))
                        .into(imageView);
                }
                else->{
                    if (resource != null) {
                        imageView?.setImageResource(resource);
                    }
                }
            }
        }
    }

}