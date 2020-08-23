package com.hux.demo.model.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.god.uikit.utils.screenSize
import com.good.framework.db.LicensePlateDao
import com.good.framework.entity.ImageData
import com.good.framework.model.uploadimg.UploadImgData
import com.good.framework.utils.Test
import com.good.framework.utils.toUploadImage
import com.hux.demo.R
import com.hux.demo.commons.AppActivity
import com.hux.demo.commons.TestApp
import com.hux.demo.databinding.ActivityMainBinding

class MainActivity : AppActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_main;

    override fun getVMClass(): Class<MainViewModel> = MainViewModel::class.java;

    override fun initView() {
        dataBinding?.mainac = this;

        var app = application as TestApp
        var lpDao = app.getDaoSession()!!.licensePlateDao;

        dataBinding?.lvLicenseplate!!.setActivity(this);
        dataBinding?.lvLicenseplate!!.setView(dataBinding?.mainLayout!!);
        var dao = (application as TestApp).getDaoSession()?.licensePlateDao;
        dataBinding?.lvLicenseplate!!.setLicensePlateDao(dao);
    }

    fun onViewClick(view: View){
        when(view?.id){
            R.id.tv_camera_upload->{
                var data = ImageData();
                data.iconFlag = true;
                data.imageFlag = true;
                data.imageWidth = screenSize(this)[0];
                data.imageHeight = 120;
                data.imageType = UploadImgData.ImageType.TYPE_SPLASH;
                data.appType = UploadImgData.AppType.APP_LIFEHOUSE;
                data.imageListFlag = true;
                data.rootPath = "what";
                data.childPath = "fuck";
                toUploadImage(this,data,"com.hux.demo");
            }
        }
    }
}