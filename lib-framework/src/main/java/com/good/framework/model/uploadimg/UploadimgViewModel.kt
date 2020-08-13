package com.good.framework.model.uploadimg

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.good.framework.commons.BaseViewModel
import com.good.framework.commons.EastViewModel
import com.good.framework.entity.ImageData
import java.io.File

class UploadimgViewModel(application: Application) : EastViewModel<UploadImgData>(application) {

    //是否具备ICON图标
    val haveIcon = MutableLiveData<Boolean>();

    //是否需要预加载图片列表
    val isLoading = MutableLiveData<Boolean>();

    val haveImage = MutableLiveData<Boolean>();

    val imageFile = MutableLiveData<File>();

    //加载关联对象的id
    var relationId: Int = 0;

    var appType:Int = 0;
    var imageType : Int = 0;
    var imageWidth : Int = 0;
    var imageHeight : Int = 0;
    var imageSize : Int = ImageData.IMAGE_SIZE_DEFAULT;

    override fun initData(): UploadImgData = UploadImgData();

    override fun initModel() {
        super.initModel()
    }


    fun imageFile(filePath:String){
        var file = File(filePath);
        imageFile.value = file;
    }


    fun initUploadData(
        iconFlag: Boolean = false,
        loadingFlag: Boolean = false,
        imageFlag : Boolean = false,
        relationId: Int = 0,
        imageWidth : Int = 0,
        imageHeight : Int = 0,
        apptype : Int = 0,
        imagetype : Int = 0,
        imageSize : Int = ImageData.IMAGE_SIZE_DEFAULT
    ) {
        haveIcon.value = iconFlag;
        isLoading.value = loadingFlag;
        haveImage.value = imageFlag;
        this.relationId = relationId;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.appType = apptype;
        this.imageHeight = imagetype;
        this.imageSize = imageSize;
    }

}