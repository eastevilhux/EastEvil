package com.good.framework.model.camera

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.Gravity
import androidx.lifecycle.Observer
import com.god.uikit.commons.Constants
import com.god.uikit.commons.Constants.Companion.REQEUST_CODE_ALBUM
import com.god.uikit.commons.Constants.Companion.REQUEST_CODE_CUTTING
import com.god.uikit.entity.Item
import com.god.uikit.presenter.MenuPresenter
import com.god.uikit.widget.LoadingDialog
import com.god.uikit.widget.window.RightMenuPopupWindow
import com.good.framework.R
import com.good.framework.commons.BaseActivity
import com.good.framework.commons.cuttingImage
import com.good.framework.databinding.ImageActivityBinding
import com.good.framework.entity.VMData
import com.good.framework.model.camera.CameraData.Companion.RESULT_CODE_CUTTING
import com.good.framework.model.camera.CameraData.Companion.RESULT_CODE_SETRESULT
import com.good.framework.model.camera.CameraData.Companion.RESULT_CODE_SHOWIMG
import com.good.framework.model.uploadimg.UploadImgData

class ImageActivity : BaseActivity<ImageActivityBinding, ImageViewModel>(), MenuPresenter {

    companion object{
        const val TAG = "ImageActivity==>";
    }

    private var menuPopup: RightMenuPopupWindow? = null;

    override fun getLayoutRes(): Int = R.layout.image_activity;

    override fun getVMClass(): Class<ImageViewModel> = ImageViewModel::class.java;

    override fun initView() {
        dataBinding?.viewModel = viewModel;
        dataBinding?.titleShowimage!!.onTitleListener = this;
        intent?.getStringExtra(UploadImgData.FILE_PATH_KEY)?.let { viewModel?.initFile(it) }
        menuPopup = RightMenuPopupWindow(this,this,R.string.menu_urcop,R.string.menu_finish)
    }

    override fun needLoadingInit(): Boolean {
        return true;
    }

    override fun vmDataChange(data: VMData) {
        super.vmDataChange(data)
        var imageData : CameraData = data as CameraData;
        if(data.code == VMData.Code.CODE_SUCCESS){
            viewModel?.showLoading();
            when(data.requestCode){
                RESULT_CODE_SHOWIMG->{

                }
                RESULT_CODE_CUTTING->{
                    cuttingImage(this,sourceFilePath = imageData.sourcePath!!,outFilePaht = imageData.outPath!!
                        ,provider = "com.life.manager")
                }
                RESULT_CODE_SETRESULT->{
                    var intent = Intent();
                    intent.putExtra(UploadImgData.FILE_PATH_KEY,imageData.outPath);
                    setResult(Constants.RESULT_COED_CAMERA,intent)
                    finish();
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_CUTTING->{
                viewModel?.cutingSuccess();
            }
        }
    }

    override fun onMenu() {
        super.onMenu()
        menuPopup?.show(dataBinding?.titleShowimage!!);
    }

    override fun onMenuItemClick(item: Item, position: Int) {
        Log.d(TAG,position.toString());
        when(position){
            0->{
                viewModel?.cuttingImage();
            }
            1->{

            }
        }
    }
}