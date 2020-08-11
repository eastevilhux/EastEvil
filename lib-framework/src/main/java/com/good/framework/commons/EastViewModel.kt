package com.good.framework.commons

import android.app.Application
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.good.framework.R
import com.good.framework.entity.Error
import com.good.framework.entity.ErrorType
import com.good.framework.entity.VMData
import com.good.framework.http.HttpConfig

abstract class EastViewModel<T:VMData?>(application: Application) : BaseViewModel(application) {

    val vmData = MutableLiveData<T>();

    val error = MutableLiveData<Error>();

    open fun initModel(){
        vmData.value = initData();
        vmData.value!!.code = VMData.Code.CODE_DEFAULT;
    }

    open fun initOnFragmentActivityCreate(){

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

    open abstract fun initData() : T;
}