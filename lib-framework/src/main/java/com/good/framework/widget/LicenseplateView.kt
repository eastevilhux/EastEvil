package com.good.framework.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.good.framework.R
import com.good.framework.databinding.LayoutLicenseplateBinding

class LicenseplateView : FrameLayout {
    private var dataBinding : LayoutLicenseplateBinding? = null;

    var abbreviationBD : Int? = null;
    var letterBD : Int? = null;

    constructor(context: Context) : super(context) {}
    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
    }


    private fun initAttrs(attrs: AttributeSet){
        var ta = context!!.obtainStyledAttributes(attrs,R.styleable.LicenseplateView);
        abbreviationBD = ta.getResourceId(R.styleable.LicenseplateView_abbreviationBackground,R.drawable.shape_license_plate);
        letterBD = ta.getResourceId(R.styleable.LicenseplateView_letterBackground,R.drawable.shape_license_plate);
        init();
    }

    private fun init(){
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_licenseplate,this,true);

        abbreviationBD?.let { dataBinding?.tvAbbreviation!!.setBackgroundResource(it) }
        letterBD?.let { dataBinding?.tvLetter!!.setBackgroundResource(it) };

    }
}