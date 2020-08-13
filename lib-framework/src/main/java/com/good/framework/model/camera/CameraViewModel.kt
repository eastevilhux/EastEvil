package com.good.framework.model.camera

import android.app.Application
import android.media.Image
import com.god.uikit.utils.isEmail
import com.god.uikit.utils.isNotNullOrEmpty
import com.good.framework.R
import com.good.framework.commons.EastViewModel
import com.good.framework.commons.mainThread
import com.good.framework.entity.VMData
import com.good.framework.utils.FileUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer

class CameraViewModel(application: Application) : EastViewModel<CameraData>(application) {
    var filePath : String? = null;

    var rootPath:String? = null;
    var childPath : String? = null;

    override fun initData(): CameraData = CameraData();


    fun saveImage(image:Image){
        showLoading()
        writeImage(image);
    }


    fun writeImage(image: Image) = GlobalScope.launch{
        try {
            var buffer: ByteBuffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes) //由缓冲区存入字节数组
            var basePath = FileUtil.getDiskPath();
            basePath?.let {
                var fileName = FileUtil.createTempFileName();
                if(rootPath == null){
                    filePath = "${basePath}/eastevil";
                }else{
                    if(rootPath!!.isNotNullOrEmpty()){
                        filePath = "${basePath}/${rootPath}";
                    }else{
                        filePath = "${basePath}/eastevil";
                    }
                }
                if(childPath == null){
                    filePath = "${filePath}/evimgs"
                }else{
                    if(childPath!!.isNotNullOrEmpty()){
                        filePath = "${filePath}/${childPath}"
                    }else{
                        filePath = "${filePath}/evimgs"
                    }
                }
                FileUtil.createDirectory(filePath);
                filePath = "${filePath}/${fileName}";
                var file = FileUtil.createFile(filePath);
                var os = FileOutputStream(file);
                var bufferedOutputStream = BufferedOutputStream(os);
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                os.close();
                filePath = file?.path;
                dismissLoading()
                mainThread {
                    vmData.value!!.code = VMData.Code.CODE_SUCCESS
                    postVMData(vmData.value!!);
                }
            }
        }catch (e:Exception){
            e.printStackTrace();
            mainThread {
                vmData.value!!.code = VMData.Code.CODE_SHOW_MSG
                vmData.value!!.msg = getApplication<Application>().getString(R.string.error_save_image);
                postVMData(vmData.value!!);
            }
        }
    }

}