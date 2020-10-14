package com.good.framework.commons

import android.app.Application
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.good.framework.R
import com.good.framework.entity.*
import com.good.framework.http.HttpConfig

abstract class EastViewModel<T:VMData?>(application: Application) : BaseViewModel(application) {

    val vmData = MutableLiveData<T>();

    val error = MutableLiveData<Error>();

    val success = MutableLiveData<SuccessResult>();

    val loading = MutableLiveData<Boolean>();

    open fun initModel(){
        vmData.value = initData();
        vmData.value!!.code = VMData.Code.CODE_DEFAULT;
        loading.value = false;
    }

    open fun initOnFragmentActivityCreate(){
        vmData.value = initData();
        vmData.value!!.code = VMData.Code.CODE_DEFAULT;
        loading.value = false;
    }

    fun postVMData(vmData: T){
        this.vmData.postValue(vmData);
    }

    fun setVMData(vmData: T){
        this.vmData.value = vmData;
    }

    fun getString(@StringRes resId : Int) : String{
        return getApplication<Application>().getString(resId);
    }

    fun getColor(@ColorRes colorRes: Int) : Int{
        return getApplication<Application>().getColor(colorRes);
    }

    fun showLoading(){
        if(isMainThread()){
            loading.value = true;
        }else{
            mainThread {
                loading.value = true;
            }
        }
    }


    fun dismissLoading(){
        if(isMainThread()){
            loading.value = false;
        }else{
            mainThread {
                loading.value = false;
            }
        }
    }

    open abstract fun initData() : T;

    open fun error(code:Int,msg:String? = getString(R.string.error_unknow),index:Int = 0){
        var er = error.value?:Error();
        when(code){
            -1-> {
                er.type = ErrorType.ERROR_SYSTEM
                er.code = -1;
                er.msg = getApplication<Application>().getString(R.string.error_system);
            }
            HttpConfig.CODE_NETWORK->{
                er.type = ErrorType.ERROR_NETWORK
                er.code = HttpConfig.CODE_NETWORK;
                er.msg = getApplication<Application>().getString(R.string.error_network);
            }
            HttpConfig.CODE_SERVICE_ERROR->{
                er.type = ErrorType.ERROR_SERVICE
                er.code = HttpConfig.CODE_SERVICE_ERROR;
                er.msg = getApplication<Application>().getString(R.string.error_service);
            }
            HttpConfig.CODE_LOGIN->{
                er.type = ErrorType.ERROR_LOGIN;
                er.code = HttpConfig.CODE_LOGIN;
                er.msg = "no login now";
            }
            else->{
                er.type = ErrorType.ERROR_UNKNOW
                er.code = code;
                er.msg = msg;
            }
        }
        er.index = index;
        error(er);
    }

    open fun error(code : Int,msg : String? = getString(R.string.error_unknow)){
        mainThread {
            var er = error.value?:Error();
            when(code){
                -1-> {
                    er.type = ErrorType.ERROR_SYSTEM
                    er.code = -1;
                    er.msg = getApplication<Application>().getString(R.string.error_system);
                }
                HttpConfig.CODE_NETWORK->{
                    er.type = ErrorType.ERROR_NETWORK
                    er.code = HttpConfig.CODE_NETWORK;
                    er.msg = getApplication<Application>().getString(R.string.error_network);
                }
                HttpConfig.CODE_SERVICE_ERROR->{
                    er.type = ErrorType.ERROR_SERVICE
                    er.code = HttpConfig.CODE_SERVICE_ERROR;
                    er.msg = getApplication<Application>().getString(R.string.error_service);
                }
                HttpConfig.CODE_LOGIN->{
                    er.type = ErrorType.ERROR_LOGIN;
                    er.code = HttpConfig.CODE_LOGIN;
                    er.msg = "no login now";
                }
                else->{
                    er.type = ErrorType.ERROR_UNKNOW
                    er.code = code;
                    er.msg = msg;
                }
            }
            error.value = er;
        }
    }


    open fun error(error:Error){
        if(isMainThread()){
            this.error.value = error;
        }else{
            mainThread {
                this.error.value = error;
            }
        }
    }

    open fun success(result:SuccessResult){
        if(isMainThread()){
            success.value = result;
        }else{
            mainThread {
                success.value = result;
            }
        }
    }

    open fun successCode(code:Int){
        var result = SuccessResult(code = code);
        success(result);
    }

    open fun successType(type:Int){
        var result = SuccessResult(type = type);
        success(result);
    }

    open fun successMsg(msg:String){
        var result = SuccessResult(msg = msg);
        success(result);
    }


    override fun onDestroy(owner: LifecycleOwner?) {
        super.onDestroy(owner)
        loading.value?.let {
            if(it){
                loading.value = false;
            }
        }
    }
}