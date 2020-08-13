package com.good.framework.entity

import com.good.framework.model.uploadimg.UploadImgData
import java.io.Serializable

class ImageData :Serializable{
    var imageType : UploadImgData.ImageType = UploadImgData.ImageType.TYPE_ERROR;
    var appType : UploadImgData.AppType = UploadImgData.AppType.APP_ERROR;
    var iconFlag : Boolean = false;
    var imageFlag : Boolean = false;
    var imageListFlag : Boolean = true;
    var loadFlag : Boolean = false;
    var relationId : Int = 0;
    var imageWidth : Int = 0;
    var imageHeight : Int = 0;
    var imageSize : Int = IMAGE_SIZE_DEFAULT;
    var provider : String = "com.life.provder";
    var rootPath : String = "eastevil";
    var childPath : String = "evimgs"
    companion object{
        const val IMAGE_SIZE_DEFAULT = 8;
    }

}