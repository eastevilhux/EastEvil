package com.hux.demo.model.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.god.uikit.entity.Item
import com.god.uikit.utils.screenSize
import com.god.uikit.widget.ViewToast
import com.god.uikit.widget.dialog.CalendarDialog
import com.god.uikit.widget.dialog.ListDialog
import com.god.uikit.widget.dialog.MessageDialog
import com.god.uikit.widget.dialog.NumberpsdDialog
import com.good.framework.commons.EastConstants
import com.good.framework.db.LicensePlateDao
import com.good.framework.entity.ImageData
import com.good.framework.http.entity.City
import com.good.framework.http.entity.LicensePlate
import com.good.framework.model.city.CitylistActivity
import com.good.framework.model.uploadimg.UploadImgData
import com.good.framework.utils.Test
import com.good.framework.utils.toUploadImage
import com.good.framework.widget.LicenseplateView
import com.hux.demo.R
import com.hux.demo.commons.AppActivity
import com.hux.demo.commons.TestApp
import com.hux.demo.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppActivity<ActivityMainBinding, MainViewModel>(),
    CalendarDialog.OnCalendarListener, ListDialog.OnDialogItemClickListener,
    NumberpsdDialog.OnPasswordDialogListener, LicenseplateView.OnLicenseplateListener {

    var calendarDialog : CalendarDialog? = null;

    var listDialog : ListDialog? = null;

    var psdDialog : NumberpsdDialog? = null;

    var msgDialog : MessageDialog? = null;

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
        dataBinding?.lvLicenseplate!!.setOnLicenseplateListener(this)

        var itemList = mutableListOf<Item>();
        itemList.add(Item.Bulder().text("fuck1").haveIcon(true).buildItem(1,2).bulder());
        itemList.add(Item.Bulder().text("fuck2").buildItem(2,3).bulder());
        itemList.add(Item.Bulder().text("fuck3").haveIcon(true).buildItem(3,4).bulder());
        itemList.add(Item.Bulder().text("fuck4").buildItem(4,5).bulder());
        itemList.add(Item.Bulder().text("fuck5").buildItem(5,6).bulder());


        listDialog = ListDialog.Builder(this)
            .title("what a fuck")
            .haveTitle(true)
            .itemList(itemList)
            .onDialogItemClickListener(this)
            .selectType(ListDialog.SELECT_TYPE_MORE)
            .builder();


        psdDialog = NumberpsdDialog.Builder(this)
            .title("fuck")
            .amount("0.12")
            .typeText("sdfasdfas")
            .psdNumber(6)
            .onPasswordDialogListener(this)
            .build();

        msgDialog = MessageDialog.Builder(this)
            .title("弹框测试")
            .message("测试弹框，这里显示的是弹框的message")
            .enterText("enter")
            .cancelText("cancel")
            .build();
    }

    fun onViewClick(view: View){
        when(view?.id){
            R.id.tv_camera_upload->{
                var data = ImageData();
                data.iconFlag = true;
                data.imageWidth = screenSize(this)[0];
                data.imageHeight = 500;
                data.albumBtnRes = R.drawable.ic_photo_camera_white_24dp
                data.titleColor = R.color.colorAccent
                data.imageType = UploadImgData.ImageType.TYPE_SPLASH;
                data.appType = UploadImgData.AppType.APP_LIFEHOUSE;
                data.imageListFlag = false;
                data.cutWidth = 50f;
                data.cutHeight = 50f;
                data.rootPath = "whathux";
                data.childPath = "fuck_what";
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
            R.id.tv_listdialog->{
                listDialog!!.show();
            }
            R.id.tv_psd_dialog->{
                psdDialog!!.show();
            }
            R.id.tv_msg_dialog->{
                msgDialog!!.show();
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
                }
            }
            EastConstants.RESULT_CODE_IMAGE->{
                data?.let {
                    var iconFile = data?.getStringExtra(ImageData.ICON_KEY);
                    var imageList = data?.getStringExtra(ImageData.IMAGE_LIST_KEY);
                    Log.d("main==>","${iconFile}${imageList}")
                }
            }
        }
    }


    override fun onCalendar(dateTime: String?, tag: Int) {
        showToastShort(dateTime?:"WTF");
    }

    override fun onEnter(selectItemList: MutableList<Item>?) {
        var sb = StringBuilder();
        selectItemList?.let {
            it.forEach {
                sb.append(it.id);
            }
            ViewToast.show(this,sb.toString(),Toast.LENGTH_LONG);
        }
    }

    override fun onItemClick(position: Int, item: Item?) {
        ViewToast.show(this,"fuck=ID=>${item?.id},TAG=>${item?.tag}",Toast.LENGTH_LONG);
    }

    override fun onPassword(password: String) {
        showToastShort(password);
    }

    override fun onDismiss() {
        showToastShort("fuck???");
    }

    override fun onAlphabetic(alphabetiic: String?) {
        showToastShort(alphabetiic?:"")
    }

    override fun onAbbreviation(licensePlate: LicensePlate?) {

    }


}