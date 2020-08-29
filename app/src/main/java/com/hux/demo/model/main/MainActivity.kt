package com.hux.demo.model.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.god.uikit.utils.screenSize
import com.god.uikit.widget.dialog.CalendarDialog
import com.good.framework.commons.EastConstants
import com.good.framework.db.LicensePlateDao
import com.good.framework.entity.ImageData
import com.good.framework.http.entity.City
import com.good.framework.model.city.CitylistActivity
import com.good.framework.model.uploadimg.UploadImgData
import com.good.framework.utils.Test
import com.good.framework.utils.toUploadImage
import com.hux.demo.R
import com.hux.demo.commons.AppActivity
import com.hux.demo.commons.TestApp
import com.hux.demo.databinding.ActivityMainBinding

class MainActivity : AppActivity<ActivityMainBinding, MainViewModel>(),
    CalendarDialog.OnCalendarListener {

    var calendarDialog : CalendarDialog? = null;

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
            R.id.iv_text_image->{
                var intent = Intent(this,CitylistActivity::class.java);
                startActivityForResult(intent,1);
            }
            R.id.tv_calendar->{
                calendarDialog?:let {
                    calendarDialog = CalendarDialog.Builder(this)
                        .haveTime(true)
                        .onCalendarListener(this)
                        .containSecond(true)
                        .builder()
                }
                calendarDialog!!.show();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            EastConstants.RESULT_CODE_CITY->{
                data?.let {
                    var province = data?.getSerializableExtra(EastConstants.KEY_PROVINCE) as City?;
                    var city = data?.getSerializableExtra(EastConstants.KEY_CITY) as City?;
                    var area = data?.getSerializableExtra(EastConstants.KEY_AREA) as City?;
                    showToastShort(province?.name+ city?.name + area?.name);
                    //showToastShort(province+ city+area);
                }
            }
        }
    }

    override fun onCalendar(dateTime: String?) {
        showToastShort(dateTime?:"wtf");
    }


}