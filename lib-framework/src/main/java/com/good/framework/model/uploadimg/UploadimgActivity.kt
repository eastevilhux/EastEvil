package com.good.framework.model.uploadimg

import android.content.Intent
import android.view.View
import com.god.uikit.adapter.ImageAdapter
import com.god.uikit.entity.Image
import com.god.uikit.presenter.ImagePresenter
import com.god.uikit.widget.dialog.ImageDialog
import com.good.framework.R
import com.good.framework.commons.BaseActivity
import com.good.framework.commons.takePhoto
import com.good.framework.databinding.UploadimgActivityBinding
import com.good.framework.entity.ImageData
import com.good.framework.utils.JsonUtil
import kotlin.text.isEmpty

class UploadimgActivity : BaseActivity<UploadimgActivityBinding, UploadimgViewModel>(),
    ImageDialog.OnImageListener, ImagePresenter {
    private var dialog : ImageDialog? = null;

    private lateinit var imageData : ImageData;

    private var adapter:ImageAdapter? = null;

    override fun getLayoutRes(): Int = R.layout.uploadimg_activity;

    override fun getVMClass(): Class<UploadimgViewModel> = UploadimgViewModel::class.java;

    override fun initView() {
        dataBinding?.titleUploadimg!!.onTitleListener = this;
        dataBinding?.viewModel = viewModel;
        dataBinding?.uploadac = this;

        dialog = ImageDialog(this);
        dialog!!.setOnImageListener(this);

        adapter = ImageAdapter(null,this,this);
        dataBinding?.adapter = adapter;

        initImageDate();
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
    }

    fun viewClick(view : View){

    }

    override fun onNewIntent(data: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onAlubm(tag: Int) {

    }

    override fun onCamera(tag: Int) {
    }

    override fun onImage(image: Image, pos: Int) {
    }


}