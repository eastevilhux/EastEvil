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

    //是否具备ICON图标
    val haveIcon = MutableLiveData<Boolean>();

    //是否需要预加载图片列表
    val isLoading = MutableLiveData<Boolean>();

    val haveImage = MutableLiveData<Boolean>();

    val imageFile = MutableLiveData<File>();
    val iconFile = MutableLiveData<File>();
    val imageList = MutableLiveData<MutableList<Image>>();
    var imageListFlag : Boolean = true;
    //加载关联对象的id
    var relationId: Int = 0;

    var appType:Int = 0;
    var imageType : Int = 0;
    var imageWidth : Int = 0;
    var imageHeight : Int = 0;
    var imageSize : Int = ImageData.IMAGE_SIZE_DEFAULT;

    var chooseImageType : ChooseType? = null;

    override fun initData(): UploadImgData = UploadImgData();

    override fun initModel() {
        super.initModel()
    }


    fun initImageList(imgSize:Int,imageListFlag:Boolean = true){
        this.imageSize = imgSize;
        this.imageListFlag = imageListFlag;
        if(imageSize > 0 && imageListFlag){
            Log.d("==Image",imgSize.toString());
            //需要展示图片列表，初始化列表
            var index = 0;
            var list = mutableListOf<Image>();
            while (index < imgSize){
                if(index == 0){
                    list.add(Image(R.drawable.ic_img_camera));
                }else {
                    list.add(Image());
                }
                index++;
            }
            imageList.value = list;
        }
    }


    fun imageFile(filePath:String){
        var file = File(filePath);
        imageFile.value = file;
    }

    fun iconFile(iconPath:String){
        var file = File(iconPath);
        iconFile.value = file;
    }

    fun imageListFile(filePath:String){
        var list = mutableListOf<Image>();
        imageList.value?.forEach{
            if(it.type == Image.Type.TYPE_FILE){
                list.add(it);
            }
        }
        list.add(Image(File(filePath)));
        var size = imageSize - list.size;
        while(size > 0){
            size--;
            list.add(Image());
        }
        imageList.value = list;
    }


    fun isCanChoose():Boolean{
        if(imageListFlag){
            if(imageList.value == null){
                return true;
            }else{
                var size = imageList.value!!.filter {it.type != Image.Type.TYPE_DEFAULT}.size;
                return size < imageSize;
            }
        }else{
            return false;
        }
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


    enum class ChooseType{
        TYPE_ICON,
        TYPE_IMAGE,
        TYPE_IMGLIST
    }


}