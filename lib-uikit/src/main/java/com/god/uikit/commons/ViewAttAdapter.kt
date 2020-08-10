package com.god.uikit.commons

import android.widget.TextView
import androidx.databinding.BindingAdapter

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
    }

}