package com.god.uikit.commons

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.god.uikit.R
import com.god.uikit.entity.Item
import com.god.uikit.utils.format
import java.io.File
import java.util.*

class ViewAttAdapter {

    companion object{
        private const val TAG = "UIKIT_ViewAttAdapter==>"

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textView: TextView, text: Int) {
            when(textView.id){
                R.id.tv_daytime->textView.setText(text.toString());
                else->textView.setText(text);
            }
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textView: TextView, text: Date){
            when(textView.id){
                R.id.tv_date->{
                    textView.text = text.format("yyyy-MM-dd")
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(textView: TextView, item:Item){
            when(textView.id){
                R.id.tv_listitems->{
                    if(item.textType == Item.TEXT_TYPE_RESOURCE){
                        textView.setText(item.itemResource);
                    }else{
                        textView.setText(item.itemText);
                    }
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImage(imageView: ImageView,file:File?){
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
        fun setImage(imageView: ImageView?,url:String?){
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
        fun setImage(imageView: ImageView?,resource:Int?){
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

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImage(imageView: ImageView,item:Item){
            when(imageView.id){
                R.id.iv_listimage->{
                    if(item.imageType == Item.IMAGE_TYPE_RESOURCE){
                        Glide.with(imageView)
                            .load(item.imageResource)
                            .apply(RequestOptions()
                                .placeholder(R.drawable.ic_upload_image_default)
                                .error(R.drawable.ic_upload_image_default))
                            .into(imageView);
                    }else{
                        Glide.with(imageView)
                            .load(item.imageUrl)
                            .apply(RequestOptions()
                                .placeholder(R.drawable.ic_upload_image_default)
                                .error(R.drawable.ic_upload_image_default))
                            .into(imageView);
                    }
                }
            }
        }
    }

}