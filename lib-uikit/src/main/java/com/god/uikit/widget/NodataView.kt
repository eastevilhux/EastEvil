package com.god.uikit.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.god.uikit.R
import com.god.uikit.databinding.LayoutNodataBinding

class NodataView : FrameLayout{
    private lateinit var dataBinding : LayoutNodataBinding;
    private val nodataText = ObservableField<String>();

    constructor(context: Context) : super(context);

    constructor(@NonNull context: Context , @Nullable attrs : AttributeSet) : super(context,attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.NodataView);
        nodataText.set(ta.getString(R.styleable.NodataView_nodataText));
        initView();

    }

    private fun initView(){
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.layout_nodata, this, true)
        dataBinding.nodataText = nodataText;

    }
}