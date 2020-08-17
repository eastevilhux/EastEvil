package com.good.framework.model.camera

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.god.uikit.utils.isNotNullOrEmpty
import com.good.framework.commons.EastViewModel
import com.good.framework.commons.mainThread
import com.good.framework.entity.VMData
import com.good.framework.model.camera.CameraData.Companion.RESULT_CODE_SETRESULT
import com.good.framework.utils.FileUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class ImageViewModel(application: Application) : EastViewModel<CameraData>(application) {
    companion object{
        const val TAG = "ImageViewModel==>";
    }

    val file = MutableLiveData<File>();
    var cutFile : File? = null;

    var rootPath : String? = null;
    var childPath : String? = null;

    override fun initData(): CameraData = CameraData();

    fun initFile(path:String){
        createFile(path);
    }

    private fun createFile(path: String)=GlobalScope.launch{
        var file = File(path);
        try {
            Log.d(TAG,file.name);
            mainThread {
                this@ImageViewModel.file.value = file;
                vmData.value?.code = VMData.Code.CODE_SUCCESS;
                vmData.value?.requestCode = CameraData.RESULT_CODE_SHOWIMG;
                vmData.value?.let { postVMData(it) };
            }
        }catch (e:Exception){
            Log.d(TAG,e.message);
            e.printStackTrace()
        }
    }


    fun cuttingImage(){
        var sourcePath = file.value?.path;
        var outPath = "${FileUtil.getDiskPath()}/"
        if(rootPath == null){
            outPath = "${outPath}/eastevil"
        }else{
            if(rootPath!!.isNotNullOrEmpty()){
                outPath = "${outPath}/${rootPath}"
            }
        }
        if(childPath == null){
            outPath = "${outPath}/evimgs/cutting"
        }else{
            if(childPath!!.isNotNullOrEmpty()){
                outPath = "${outPath}/${childPath}/cutting"
            }else{
                outPath = "${outPath}/evimgs/cutting"
            }
        }
        FileUtil.createDirectory(outPath);
        outPath = "${outPath}/${FileUtil.createTempFileName()}"
        cutFile = FileUtil.createFile(outPath);
        vmData.value?.code = VMData.Code.CODE_SUCCESS;
        vmData.value?.requestCode = CameraData.RESULT_CODE_CUTTING;
        vmData.value?.outPath = outPath;
        vmData.value?.sourcePath = sourcePath;
        vmData.value?.let { setVMData(vmData = it) };
    }


    fun cutingSuccess(){
        //删除原有拍照得图片，使用裁剪后得图片
        var f = file.value;
        if(f != null && f.length() > 0){
            f.delete();
        }
        //返回上一层
        vmData.value?.code = VMData.Code.CODE_SUCCESS;
        vmData.value?.requestCode = RESULT_CODE_SETRESULT;
        vmData.value?.outPath = cutFile?.path;
        vmData.value?.let { setVMData(it) };
    }

    fun finishImage(){
        vmData.value?.code = VMData.Code.CODE_SUCCESS;
        vmData.value?.requestCode = RESULT_CODE_SETRESULT;
        vmData.value?.outPath = file.value?.path;
        vmData.value?.let { setVMData(it)}
    }



}