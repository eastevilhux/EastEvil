package com.good.framework.model.uploadimg

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.god.uikit.entity.Image
import com.good.framework.R
import com.good.framework.commons.BaseViewModel
import com.good.framework.commons.EastViewModel
import com.good.framework.entity.Error
import com.good.framework.entity.ImageData
import com.good.framework.entity.VMData
import com.good.framework.model.uploadimg.UploadimgActivity.Companion.TAG_ICON
import com.good.framework.utils.FileUtil
import java.io.File

class UploadimgViewModel(application: Application) : EastViewModel<UploadImgData>(application) {

    val imageData = MutableLiveData<ImageData>();
    val imageList = MutableLiveData<MutableList<Image>>();
    val iconImage = MutableLiveData<Image>();

    override fun initData(): UploadImgData = UploadImgData();

    override fun initModel() {
        super.initModel()
    }

    fun setImageData(imageData: ImageData){
        this.imageData.value = imageData;
        if(imageData.imageListFlag){
            initImageList(imageData.imageSize);
        }
    }

    fun disposeImage(uriList : MutableList<Uri>,tag : Int){
        if(uriList == null || uriList.size == 0){
            error(Error.CODE_VIEW_MSG,getString(R.string.please_select_image))
            return;
        }
        if(tag == TAG_ICON){
            //选择图标
            var file = FileUtil.uriToFile(getApplication(),uriList[0]);
            file?.let {
                iconImage.value = Image(it);
            }?:let {
                error(Error.CODE_VIEW_MSG,getString(R.string.please_select_image))
            }
        }else{
            //选择图片列表
        }
    }

    private fun initImageList(size : Int = 8){
        var i = 0;
        var image : Image? = null;
        var list = mutableListOf<Image>();
        while(i<size){
            if(i == 0){
                image = Image(R.drawable.ic_upload_image_default);
            }else{
                image = Image();
            }
            list.add(image);
            i++;
        }
        imageList.value = list;
    }

}