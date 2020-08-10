package com.good.framework.commons

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.good.framework.entity.VMData

abstract class EastViewModel<T:VMData?>(application: Application) : BaseViewModel(application) {

    val vmData = MutableLiveData<T>();

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

    open abstract fun initData() : T;
}