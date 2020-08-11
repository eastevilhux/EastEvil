package com.good.framework.commons

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.god.uikit.widget.TitleLayout
import com.god.uikit.widget.ViewToast
import com.good.framework.R
import com.good.framework.entity.Error
import com.good.framework.entity.ErrorType
import com.good.framework.entity.VMData
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseActivity<D : ViewDataBinding,V : EastViewModel<*>> : AppCompatActivity(),TitleLayout.OnTitleListener{
    val DEFAULT_CODE = 100;
    var dataBinding : D? = null;
    var viewModel:V? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,getLayoutRes())
        var vp = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        viewModel = vp.get(getVMClass()!!);
        viewModel?.setLifecycleOwner(this);
        lifecycle.addObserver(viewModel!!);
        dataBinding?.lifecycleOwner = this;
        viewModel?.initModel();
        initView();

        viewModel?.vmData?.observe(this, Observer {
            it?.let {
                vmDataChange(it);
            }
        })

        viewModel?.error?.observe(this, Observer {
            when(it.type){
                ErrorType.ERROR_LOGIN->{
                    loginError();
                }
                ErrorType.ERROR_UNKNOW->{
                    reqeustError(it);
                }
                else->{
                    showToastShort(it.msg?:getString(R.string.error_unknow));
                }
            }
        })

        intent?.let {
            initIntentData(it);
        }
    }

    abstract fun getLayoutRes() : Int;

    abstract fun getVMClass() : Class<V>;

    abstract fun initView();

    open fun vmDataChange(data:VMData){

    }

    open fun reqeustError(error:Error){

    }

    open fun loginError(){

    }

    override fun onMenu() {

    }

    override fun onBack() {
        back()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            back();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    open fun back(){
        finish()
    }

    open fun initIntentData(intent : Intent){

    }

    fun showToastShort(@StringRes strRes:Int){
        ViewToast.show(this,strRes, Toast.LENGTH_SHORT);
    }

    fun showToastShort(strRes:String){
        ViewToast.show(this,strRes,Toast.LENGTH_SHORT);
    }

    fun showToastLong(@StringRes strRes:Int){
        ViewToast.show(this,strRes,Toast.LENGTH_LONG);
    }

    fun showToastLong( strRes:String){
        ViewToast.show(this,strRes,Toast.LENGTH_LONG);
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //将结果转发给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}