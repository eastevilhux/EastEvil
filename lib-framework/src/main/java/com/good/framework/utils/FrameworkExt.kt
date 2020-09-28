package com.good.framework.utils

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.god.uikit.commons.Constants.Companion.REQEUST_CODE_UPLOADIMG
import com.good.framework.commons.toJSON
import com.good.framework.entity.ImageData
import com.good.framework.model.uploadimg.UploadImgData
import com.good.framework.model.uploadimg.UploadimgActivity


fun toUploadImage(activity:Activity,uploadImgData: ImageData,provider:String){
    uploadImgData.provider = provider;
    var intent = Intent(activity,UploadimgActivity::class.java);
    var json = uploadImgData.toJSON();
    intent.putExtra(ImageData.DATA_KEY,json);
    activity.startActivityForResult(intent,REQEUST_CODE_UPLOADIMG)
}
