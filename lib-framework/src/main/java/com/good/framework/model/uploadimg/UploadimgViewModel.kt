package com.good.framework.model.uploadimg

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.god.uikit.entity.Image
import com.good.framework.R
import com.good.framework.commons.BaseViewModel
import com.good.framework.commons.EastViewModel
import com.good.framework.entity.ImageData
import java.io.File

class UploadimgViewModel(application: Application) : EastViewModel<UploadImgData>(application) {

    val imageData = MutableLiveData<ImageData>();

    override fun initData(): UploadImgData = UploadImgData();

    override fun initModel() {
        super.initModel()
    }

    fun setImageData(imageData: ImageData){
        this.imageData.value = imageData;
    }



}