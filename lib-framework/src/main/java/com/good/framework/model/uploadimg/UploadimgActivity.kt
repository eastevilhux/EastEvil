package com.good.framework.model.uploadimg

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.god.uikit.adapter.ImageAdapter
import com.god.uikit.commons.Constants
import com.god.uikit.entity.Image
import com.god.uikit.presenter.ImagePresenter
import com.god.uikit.utils.toAlubm
import com.god.uikit.widget.dialog.ImageDialog
import com.good.framework.R
import com.good.framework.commons.BaseActivity
import com.good.framework.commons.EastConstants
import com.good.framework.commons.cuttingImage
import com.good.framework.commons.takePhoto
import com.good.framework.databinding.UploadimgActivityBinding
import com.good.framework.entity.Error
import com.good.framework.entity.ImageData
import com.good.framework.entity.VMData
import com.good.framework.utils.JsonUtil
import com.zhihu.matisse.Matisse
import kotlin.text.isEmpty

class UploadimgActivity : BaseActivity<UploadimgActivityBinding, UploadimgViewModel>(),
    ImageDialog.OnImageListener, ImagePresenter {
    private lateinit var dialog : ImageDialog;

    private lateinit var imageData : ImageData;

    private lateinit var adapter:ImageAdapter;

    override fun getLayoutRes(): Int = R.layout.uploadimg_activity;

    override fun getVMClass(): Class<UploadimgViewModel> = UploadimgViewModel::class.java;

    override fun initView() {
        dataBinding?.titleUploadimg!!.onTitleListener = this;
        dataBinding?.viewModel = viewModel;
        dataBinding?.uploadac = this;

        dialog = ImageDialog(this);
        dialog.setOnImageListener(this);

        adapter = ImageAdapter(null,this,this);
        dataBinding?.adapter = adapter;

        initImageDate();

        viewModel?.imageList!!.observe(this, Observer {
            adapter.list = it;
            adapter.notifyDataSetChanged();
        })
    }


    private fun initImageDate(){
        var json = intent.getStringExtra(ImageData.DATA_KEY);
        if(json == null || json.isEmpty()){
            showToastShort(R.string.error_system);
            back();
            return;
        }
        var data = JsonUtil.instance.getGson().fromJson(json,ImageData::class.java);
        if(data == null){
            showToastShort(R.string.error_system);
            back();
            return;
        }
        viewModel!!.setImageData(data);
        dialog.setIcons(data.albumBtnRes,data.cameraBtnRes);
        dataBinding?.titleUploadimg?.setBackgroundResource(data.titleColor);
    }

    fun viewClick(view : View){
        when(view.id){
            R.id.iv_icon->{
                //选择图标
                if(!dialog.isShowing){
                    dialog.tag = TAG_ICON;
                    dialog.show();
                }
            }
            R.id.btn_upload->{
                viewModel?.uploadImage();
            }
        }
    }

    override fun reqeustError(error: Error) {
        super.reqeustError(error)
        showToastShort(error.msg?:getString(R.string.error_save_image))
    }

    override fun onNewIntent(data: Intent?) {
        super.onNewIntent(intent)
        data?.let {
            viewModel?.disposeCamera(it.getStringExtra(ImageData.FILE_PATH_KEY),dialog.tag);
        }
    }

    override fun onAlubm(tag: Int) {
        if(tag == TAG_ICON){
            //选择图标
            toAlubm(activity = this,maxSelectable = 1);
        }else{
            toAlubm(activity = this,maxSelectable = viewModel?.imageData?.value?.imageSize?:8);
        }
    }

    override fun onCamera(tag: Int) {
        dialog.dismiss();
        takePhoto(activity = this,imageData = viewModel?.imageData?.value!!);
    }

    override fun onImage(image: Image, pos: Int) {
        if(!dialog.isShowing){
            viewModel?.setCameraIndex(pos);
            dialog.tag = TAG_IMAGELIST;
            dialog.show();
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            if (requestCode == Constants.REQEUST_CODE_ALBUM) {
                var data = Matisse.obtainResult(data);
                viewModel?.disposeImage(data, dialog.tag);
                dialog.dismiss();
            } else if(requestCode == Constants.REQUEST_CODE_CUTTING){
                //裁剪界面返回
                viewModel?.cuttingSuccess();
            }
        }
    }

    override fun vmDataChange(data: VMData) {
        super.vmDataChange(data)
        var uploadImgData = data as UploadImgData;
        when(uploadImgData.code){
            VMData.Code.CODE_SUCCESS->{
                //标识成功
                if(uploadImgData.requestCode == UploadImgData.CODE_TO_CUTTING){
                    //需要跳转进入裁剪界面
                    cuttingImage(activity = this,sourceFilePath = uploadImgData.sourceFilePath!!,
                        outFilePaht = uploadImgData.cutFilePath!!,aspectRatioX = uploadImgData.cutWidth,
                        aspectRatioY = uploadImgData.cutHeight,provider = uploadImgData.provider!!)
                }else if(uploadImgData.requestCode == UploadImgData.CODE_TO_UPLOAD){
                    var intent = Intent();
                    intent.putExtra(ImageData.ICON_KEY,uploadImgData.iconFilePath);
                    intent.putExtra(ImageData.IMAGE_LIST_KEY,uploadImgData.imageListPath);
                    setResult(EastConstants.RESULT_CODE_IMAGE,intent);
                    finish();
                }
            }
        }
    }

    companion object{
        private const val TAG = "UploadimgActivity==>";

        const val TAG_ICON = 0x01;
        const val TAG_IMAGELIST = 0x02;

    }
}