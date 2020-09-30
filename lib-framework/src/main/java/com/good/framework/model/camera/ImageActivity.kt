package com.good.framework.model.camera

import android.content.Intent
import android.util.Log
import com.god.uikit.commons.Constants.Companion.REQUEST_CODE_CUTTING
import com.god.uikit.entity.Item
import com.god.uikit.presenter.MenuPresenter
import com.god.uikit.utils.isEmpty
import com.god.uikit.widget.window.RightMenuPopupWindow
import com.good.framework.R
import com.good.framework.commons.BaseActivity
import com.good.framework.commons.cuttingImage
import com.good.framework.databinding.ImageActivityBinding
import com.good.framework.entity.ImageData
import com.good.framework.entity.VMData
import com.good.framework.model.camera.CameraData.Companion.RESULT_CODE_CUTTING
import com.good.framework.model.camera.CameraData.Companion.RESULT_CODE_SETRESULT
import com.good.framework.model.camera.CameraData.Companion.RESULT_CODE_SHOWIMG
import com.good.framework.model.uploadimg.UploadimgActivity
import com.good.framework.utils.JsonUtil
import com.yalantis.ucrop.UCropActivity

class ImageActivity : BaseActivity<ImageActivityBinding, ImageViewModel>(), MenuPresenter {

    companion object{
        const val TAG = "ImageActivity==>";
    }

    private var menuPopup: RightMenuPopupWindow? = null;
    private lateinit var imageData: ImageData;

    override fun getLayoutRes(): Int = R.layout.image_activity;

    override fun getVMClass(): Class<ImageViewModel> = ImageViewModel::class.java;

    override fun initView() {
        dataBinding?.viewModel = viewModel;
        dataBinding?.titleShowimage!!.onTitleListener = this;
        var path = intent.getStringExtra(ImageData.FILE_PATH_KEY)


        var json = intent.getStringExtra(ImageData.DATA_KEY);
        if(json == null){
            showToastShort(R.string.error_system);
            back();
            return;
        }
        imageData = JsonUtil.instance.getGson().fromJson(json,ImageData::class.java);
        viewModel?.initFile(path)
        viewModel?.rootPath = imageData.rootPath;
        viewModel?.childPath = imageData.childPath;
        menuPopup = RightMenuPopupWindow(this,this,R.string.menu_urcop,R.string.menu_finish)

        dataBinding?.titleShowimage!!.setBackgroundResource(imageData.titleColor);
    }


    override fun vmDataChange(data: VMData) {
        super.vmDataChange(data)
        var imageData : CameraData = data as CameraData;
        if(data.code == VMData.Code.CODE_SUCCESS){
            when(data.requestCode){
                RESULT_CODE_SHOWIMG->{

                }
                RESULT_CODE_CUTTING->{
                    if(this.imageData.provider == null || this.imageData.provider?.isEmpty() == true){
                        throw IllegalAccessException("nuknow the provinder");
                    }
                    cuttingImage(this, sourceFilePath = imageData.sourcePath!!, outFilePaht = imageData.outPath!!
                        , provider = this.imageData.provider!!,aspectRatioY = this.imageData.cutHeight,
                        aspectRatioX = this.imageData.cutWidth)
                }
                RESULT_CODE_SETRESULT->{
                    var intent = Intent(this,UploadimgActivity::class.java);
                    intent.putExtra(ImageData.FILE_PATH_KEY,imageData.outPath);
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