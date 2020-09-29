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
import com.good.framework.commons.takePhoto
import com.good.framework.databinding.UploadimgActivityBinding
import com.good.framework.entity.ImageData
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
        }
    }

    override fun onNewIntent(data: Intent?) {
        super.onNewIntent(intent)
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
        takePhoto(activity = this,imageData = viewModel?.imageData?.value!!);
    }

    override fun onImage(image: Image, pos: Int) {
        if(!dialog.isShowing){
            dialog.tag = TAG_IMAGELIST;
            dialog.show();
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQEUST_CODE_ALBUM){
            var data = Matisse.obtainResult(data);
            viewModel?.disposeImage(data,dialog.tag);
        }
    }


    companion object{
        private const val TAG = "UploadimgActivity==>";

        const val TAG_ICON = 0x01;
        const val TAG_IMAGELIST = 0x02;

    }
}