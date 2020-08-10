package com.god.uikit.widget.dialog

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.god.uikit.R
import com.god.uikit.commons.Constants
import com.god.uikit.databinding.DialogImageBinding
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.dip2Px
import com.god.uikit.widget.ViewToast
import pub.devrel.easypermissions.EasyPermissions

class ImageDialog(context: Context) : Dialog(context, R.style.DialogStyle),EasyPermissions.PermissionCallbacks {

    var dataBinding : DialogImageBinding;

    var tag : Int = 0;

    private var myContent : Context? = null;

    private var onImageListener : OnImageListener? = null;

    init {
        myContent = context;
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_image,null,false);
        dataBinding?.dialog = this;
        setContentView(dataBinding.root)

        val dialogWindow = window
        val lp = dialogWindow!!.attributes

        lp.width = ViewUtil.getScreenSize(context)[0] - 100.dip2Px();
        lp.height = ViewUtil.dip2px(context, 120);
        //lp.alpha = 0.7f; // 透明度
        //lp.alpha = 0.7f; // 透明度
        dialogWindow!!.attributes = lp
    }


    fun albumClick(view :View){
        //判断是否获取到相册权限
        if(EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)){
            if(onImageListener != null)
                onImageListener!!.onAlubm(tag);
        }else{
            //请求权限
            var reqeustMsg = context.getString(R.string.request_per_album);
            when(myContent){
                is Activity ->{
                    EasyPermissions.requestPermissions(myContent as Activity,reqeustMsg,Constants.REQEUST_CODE_ALBUM,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                is Fragment->{
                    EasyPermissions.requestPermissions(myContent as Fragment,reqeustMsg,Constants.REQEUST_CODE_ALBUM ,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    fun camearClick(view : View){
        //判断是否获取到相机权限
        if(EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)){
            onImageListener?.let {
                it.onCamera(tag);
            }
        }else{
            //请求权限
            var reqeustMsg = context.getString(R.string.per_error_camera);
            when(myContent){
                is Activity ->{
                    EasyPermissions.requestPermissions(myContent as Activity,reqeustMsg,Constants.REQEUST_CODE_CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                }
                is Fragment->{
                    EasyPermissions.requestPermissions(myContent as Fragment,reqeustMsg,Constants.REQEUST_CODE_CAMERA ,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                }
            }
        }
    }

    fun setOnImageListener(onImageListener:OnImageListener){
        this.onImageListener = onImageListener;
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        myContent?.let { ViewToast.show(it,R.string.per_error_album,Toast.LENGTH_LONG) }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(requestCode == Constants.REQEUST_CODE_ALBUM){
            onImageListener?.let {
                it.onAlubm(tag);
            }
        }else{
            onImageListener?.let {
                it.onCamera(tag);
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }


    public interface OnImageListener{

        fun onAlubm(tag:Int);

        fun onCamera(tag:Int);
    }
}