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
import com.god.uikit.utils.isEmpty
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
import com.good.framework.model.uploadimg.UploadImgData.Companion.CHILD_PATH_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.PROVINDER_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.ROOT_PAHT_KEY
import com.good.framework.model.uploadimg.UploadimgActivity
import com.yalantis.ucrop.UCropActivity

class ImageActivity : BaseActivity<ImageActivityBinding, ImageViewModel>(), MenuPresenter {

    companion object{
        const val TAG = "ImageActivity==>";
    }

    private var menuPopup: RightMenuPopupWindow? = null;
    private var provider : String? = null;

    override fun getLayoutRes(): Int = R.layout.image_activity;

    override fun getVMClass(): Class<ImageViewModel> = ImageViewModel::class.java;

    override fun initView() {
        dataBinding?.viewModel = viewModel;
        dataBinding?.titleShowimage!!.onTitleListener = this;
        intent?.getStringExtra(UploadImgData.FILE_PATH_KEY)?.let { viewModel?.initFile(it) }
        provider = intent?.getStringExtra(PROVINDER_KEY);
        var rootPath = intent?.getStringExtra(ROOT_PAHT_KEY);
        var childPath = intent?.getStringExtra(CHILD_PATH_KEY);
        viewModel?.rootPath = rootPath;
        viewModel?.childPath = childPath;
        menuPopup = RightMenuPopupWindow(this,this,R.string.menu_urcop,R.string.menu_finish)
    }


    override fun vmDataChange(data: VMData) {
        super.vmDataChange(data)
        var imageData : CameraData = data as CameraData;
        if(data.code == VMData.Code.CODE_SUCCESS){
            when(data.requestCode){
                RESULT_CODE_SHOWIMG->{

                }
                RESULT_CODE_CUTTING->{
                    if(provider == null || provider?.isEmpty() == true){
                        throw IllegalAccessException("nuknow the provinder");
                    }
                    cuttingImage(this,sourceFilePath = imageData.sourcePath!!,outFilePaht = imageData.outPath!!
                        ,provider = provider!!)
                }
                RESULT_CODE_SETRESULT->{
                    var intent = Intent(this,UploadimgActivity::class.java);
                    intent.putExtra(UploadImgData.FILE_PATH_KEY,imageData.outPath);
                    startActivity(intent)
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
                viewModel?.finishImage();
            }
        }
    }

    override fun needLoadingInit(): Boolean {
        return false;
    }
}