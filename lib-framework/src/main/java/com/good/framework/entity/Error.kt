package com.good.framework.entity

import java.nio.charset.Charset

class Error {
    var code : Int = 0;
    var type : ErrorType = ErrorType.ERROR_DEFAULT;
    var msg : String? = "unknow error";

    companion object{
        const val CODE_SUCCESS = 66;
        const val CODE_ERROR = -1;
        const val CODE_LOGIN = -2;
        const val CODE_NETWORK = 404;
        const val CODE_EMPTY = 44;
        const val CODE_SERVICE_ERROR = -4;

        const val CODE_VIEW_MSG = -3;
    }
}