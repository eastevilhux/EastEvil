package com.good.framework.model.uploadimg

import java.io.Serializable

class ImageInfo constructor(var appType: UploadImgData.AppType,var imgType: UploadImgData.ImageType) : Serializable{
    var iconFlag: Boolean = false;
    var loadingFlag: Boolean = false;
    var relationId: Int = 0;
    var imageFlag: Boolean = false;
    var imageWidth: Int = 0;
    var imageHeight: Int = 0;
    var imageSize : Int = DEFATUL_IMAGE_SIZE;

    companion object{
        const val DEFATUL_IMAGE_SIZE = 8;
    }
}