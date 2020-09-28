package com.good.framework.entity

import com.good.framework.R
import com.good.framework.model.uploadimg.UploadImgData
import java.io.Serializable

class ImageData :Serializable{
    var imageType : UploadImgData.ImageType = UploadImgData.ImageType.TYPE_ERROR;
    var appType : UploadImgData.AppType = UploadImgData.AppType.APP_ERROR;
    var provider : String = "com.life.provder";
    var rootPath : String = "eastevil";
    var childPath : String = "evimgs"
    var titleColor : Int = R.color.ColorBtnNor
    var iconFlag : Boolean = false;
    var imageListFlag : Boolean = true;
    var loadFlag : Boolean = false;
    var relationId : Int = 0;
    var imageWidth : Int = 0;
    var imageHeight : Int = 0;
    var imageSize : Int = IMAGE_SIZE_DEFAULT;
    var cameraWidth : Int = 0;
    var cameraHeight : Int = 0;
    var cutWidth : Int = 0;
    var cutHeight : Int = 0;

    companion object{
        const val IMAGE_SIZE_DEFAULT = 8;

        const val DATA_KEY = "uploadimagedata_key";
        const val FILE_PATH_KEY = "uploadfilepath_key";
    }

}