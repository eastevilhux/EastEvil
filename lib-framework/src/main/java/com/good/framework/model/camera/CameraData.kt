package com.good.framework.model.camera

import com.good.framework.entity.VMData

class CameraData : VMData() {

    companion object{
        const val RESULT_CODE_SHOWIMG = 1;
        const val RESULT_CODE_CUTTING = 2;
        const val RESULT_CODE_SETRESULT = 3;
    }
    var sourcePath:String? = null;
    var outPath:String? = null;
}
