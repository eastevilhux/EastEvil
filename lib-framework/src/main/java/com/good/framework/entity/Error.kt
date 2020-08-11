package com.good.framework.entity

class Error {
    var code : Int = 0;
    var type : ErrorType = ErrorType.ERROR_DEFAULT;
    var msg : String? = "unknow error";
}