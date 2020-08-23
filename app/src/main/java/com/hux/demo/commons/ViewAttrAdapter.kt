package com.hux.demo.commons

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.god.uikit.commons.GlideRoundTransform
import com.hux.demo.R

class ViewAttrAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImage(view: ImageView, icon: Int) {
            when (view.id) {
                R.id.iv_text_image->{
                    when(icon){
                        1->view.setImageResource(R.mipmap.ic_launcher)
                        2->view.setImageResource(R.mipmap.ic_launcher_round)
                        else->view.setImageResource(R.mipmap.ic_launcher);
                    }
                }
                else -> view.setImageResource(icon)
            }
        }
    }
}