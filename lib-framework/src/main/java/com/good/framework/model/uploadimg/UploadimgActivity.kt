package com.good.framework.model.uploadimg

import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.toAlubm
import com.god.uikit.widget.ViewToast
import com.god.uikit.widget.dialog.ImageDialog
import com.good.framework.R
import com.good.framework.commons.BaseActivity
import com.good.framework.commons.takePhoto
import com.good.framework.databinding.UploadimgActivityBinding
import kotlin.math.max

class UploadimgActivity : BaseActivity<UploadimgActivityBinding, UploadimgViewModel>(),
    ImageDialog.OnImageListener {
    private var dialog : ImageDialog? = null;

    override fun getLayoutRes(): Int = R.layout.uploadimg_activity;

    override fun getVMClass(): Class<UploadimgViewModel> = UploadimgViewModel::class.java;

    override fun initView() {
        dataBinding?.titleUploadimg!!.onTitleListener = this;
        dataBinding?.viewModel = viewModel;
        dataBinding?.uploadac = this;

        dialog = ImageDialog(this);
        dialog!!.setOnImageListener(this);
    }

    override fun initIntentData(intent: Intent) {
        super.initIntentData(intent)
        var info : ImageInfo = intent.getSerializableExtra(UploadImgData.IMAGE_INFO_KEY) as ImageInfo;
        var apptype = info.appType;
        if (apptype == UploadImgData.AppType.APP_ERROR) {
            ViewToast.show(this, R.string.upload_img_error_unknowapptype, Toast.LENGTH_SHORT);
            back();
        }
        var imageType = info.imgType;
        if(imageType == UploadImgData.ImageType.TYPE_ERROR){
            ViewToast.show(this, R.string.upload_img_error_unknowimgtype, Toast.LENGTH_SHORT);
            back()
        }
        var iconFlag = info.iconFlag;
        var imageFlag = info.imageFlag;
        var loadingFlag = info.loadingFlag;
        var relationId = info.relationId;
        var imageWidth = info.imageWidth;
        var imageHeight = info.imageHeight;
        var size = info.imageSize;
        viewModel?.initUploadData(iconFlag,loadingFlag,imageFlag,relationId,
            imageWidth,imageHeight,apptype.getType(),imageType.getType(),size);
        if(imageFlag){
            var params = dataBinding?.ivImage!!.layoutParams;
            params.width = imageWidth;
            var maxHeight = ViewUtil.getScreenSize(this)[1]/3;
            if(imageHeight >= maxHeight){
                imageHeight = maxHeight;
            }
            params.height = imageHeight;
            dataBinding?.ivImage!!.layoutParams = params;
        }
    }

    fun viewClick(view : View){
        when(view?.id){
            R.id.iv_image->chooseImage();
        }
    }

    private fun chooseImage(){
        dialog?.let {
            if(!it.isShowing){
                it.tag = TAG_TYPE_IMAGE;
                it.show();
            }
        }
    }

    override fun onAlubm(tag: Int) {
        //调用知乎api，进入相册
        if(tag == TAG_TYPE_IMAGE){
            toAlubm(activity = this,maxSelectable = 1,mimeType = 1)
        }
    }

    override fun onCamera(tag: Int) {
        if(tag == TAG_TYPE_IMAGE){
            var width = viewModel?.imageWidth?:0;
            var heigh = viewModel?.imageHeight?:0;
            if(width == 0 || heigh == 0){
                var size = ViewUtil.getScreenSize(this);
                if(width == 0){
                    width = size[0];
                }
                if(heigh == 0){
                    heigh = size[1];
                }
            }
            takePhoto(activity = this,width = width,height = heigh);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG,resultCode.toString());
    }

    companion object{
        const val TAG_TYPE_IMAGE = 1;
        const val TAG = "UploadimgActivity=>";
    }

}