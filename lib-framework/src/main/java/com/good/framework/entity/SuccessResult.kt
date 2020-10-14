package com.good.framework.entity

data class SuccessResult(val code : Int = 0, val type : Int = 0, val msg : String? = "unknow success"){

    companion object{
        const val DEFAULT_CODE = 0;
        const val DEFAULT_TYPE = 0;
    }
}