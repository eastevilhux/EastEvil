package com.good.framework.model.uploadimg

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.god.uikit.entity.Image
import com.god.uikit.utils.isNotNullOrEmpty
import com.good.framework.R
import com.good.framework.commons.BaseViewModel
import com.good.framework.commons.EastViewModel
import com.good.framework.commons.toJSON
import com.good.framework.entity.Error
import com.good.framework.entity.ImageData
import com.good.framework.entity.VMData
import com.good.framework.model.uploadimg.UploadimgActivity.Companion.TAG_ICON
import com.good.framework.utils.FileUtil
import java.io.File

class UploadimgViewModel(application: Application) : EastViewModel<UploadImgData>(application) {

    val imageData = MutableLiveData<ImageData>();
    val imageList = MutableLiveData<MutableList<Image>>();
    val iconImage = MutableLiveData<Image>();
    //临时文件
    var tempFile : File ? = null;
    private var cameraIndex : Int = -1;

    override fun initData(): UploadImgData = UploadImgData();

    override fun initModel() {
        super.initModel()
    }

    fun setImageData(imageData: ImageData){
        this.imageData.value = imageData;
        if(imageData.imageListFlag){
            initImageList(imageData.imageSize);
        }
        if(imageData.iconFlag){
            var image = Image(R.drawable.ic_upload_image_default);
            iconImage.value = image;
        }
    }

    /**
     * 如果为列表选择拍照，记录点击的列表中的下表,再拍照成功后，进行替换原有列表中的对象
     * create by Administrator at 2020/9/30 14:37
     * @author Administrator
     * @param index
     *      选中的下表
     * @return
     *      void
     */
    fun setCameraIndex(index:Int){
        this.cameraIndex = index;
    }

    /**
     * 处理拍照或者选择图库后的逻辑
     * create by Administrator at 2020/9/30 14:31
     * @author Administrator
     * @param uriList
     *      选择的图库文件uri列表
     * @param tag
     *      图标和列表标识
     * @return
     *      void
     */
    fun disposeImage(uriList : MutableList<Uri>,tag : Int){
        if(uriList == null || uriList.size == 0){
            error(Error.CODE_VIEW_MSG,getString(R.string.please_select_image))
            return;
        }
        if(tag == TAG_ICON){
            //选择图标
            var file = FileUtil.uriToFile(getApplication(),uriList[0]);
            file?.let {
                iconImage.value = Image(it);
                //是否需要按要求裁剪
                if(imageData.value?.cutWidth?:0 != 0 && imageData.value?.cutHeight?:0 != 0){
                    //如果裁剪宽高均不为0，则认为需要裁剪,跳转进入裁剪界面
                    cuttingImage(it);
                }
            }?:let {
                error(Error.CODE_VIEW_MSG,getString(R.string.please_select_image))
            }
        }else{
            //选择图片列表
            var list = mutableListOf<Image>();
            for(uri in uriList){
                var file = FileUtil.uriToFile(getApplication(),uriList[0]);
                file?.let {
                    list.add(Image(it));
                }
            }
            var needNum = imageData.value!!.imageSize - list.size;
            while (needNum > 0){
                list.add(Image());
                needNum--;
            }
            imageList.value = list;
        }
    }

    /**
     * 处理拍照后的图片
     * create by Administrator at 2020/9/30 14:35
     * @author Administrator
     * @param path
     *      拍照后的文件路径
     * @param tag
     *      图标和列表标识
     * @return
     *      void
     */
    fun disposeCamera(path:String,tag:Int){
        if(path == null || path.isNullOrEmpty()){
            error(Error.CODE_VIEW_MSG,getString(R.string.please_select_image))
            return;
        }
        if(tag == TAG_ICON){
            var file = File(path);
            iconImage.value = Image(file);
        }else{
            //选择的为列表进行拍照
            var image = imageList.value!!.get(cameraIndex);
            //修改该Image对象的数据
            image.type = Image.Type.TYPE_FILE;
            image.file = File(path);
            imageList.value!!.set(cameraIndex,image);
            imageList.value = imageList.value;
        }
    }

    /**
     * 裁剪界面成功返回
     * create by Administrator at 2020/9/30 14:11
     * @author hux
     * @return
     *      void
     */
    fun cuttingSuccess(){
        tempFile?.let {
            iconImage.value = Image(it);
        }?:let {
            error(Error.CODE_SERVICE_ERROR,getString(R.string.cutting_error));
        }
    }


    fun uploadImage(){
        var iconFile : File ? = null;
        if(imageData.value!!.iconFlag){
            iconFile = iconImage.value?.file;
            if(iconFile == null || iconFile.length() == 0L){
                error(Error.CODE_VIEW_MSG,getString(R.string.please_select_image))
                return;
            }
        }
        var list = mutableListOf<File>();
        if(imageData.value!!.imageListFlag){
            imageList.value?.let {
                for(i in it){
                    if(i.type == Image.Type.TYPE_FILE){
                        i.file?.let { it1 -> list.add(it1) };
                    }
                }
            }
            if(list == null || list.isEmpty()){
                error(Error.CODE_VIEW_MSG,getString(R.string.please_select_image))
                return;
            }
        }
        vmData.value!!.code = VMData.Code.CODE_SUCCESS;
        vmData.value!!.requestCode = UploadImgData.CODE_TO_UPLOAD;
        vmData.value!!.iconFilePath = iconFile?.path;
        var listJson = list.toJSON();
        vmData.value!!.imageListPath = listJson;
        setVMData(vmData.value!!);
    }

    private fun initImageList(size : Int = 8){
        var i = 0;
        var image : Image? = null;
        var list = mutableListOf<Image>();
        while(i<size){
            if(i == 0){
                image = Image(R.drawable.ic_upload_image_default);
            }else{
                image = Image();
            }
            list.add(image);
            i++;
        }
        imageList.value = list;
    }

    private fun cuttingImage(oldFile : File){
        vmData.value?.let {
            it.code = VMData.Code.CODE_SUCCESS;
            it.requestCode = UploadImgData.CODE_TO_CUTTING;
            it.cutWidth = imageData.value?.cutWidth?:0f;
            it.cutHeight = imageData.value?.cutHeight?:0f;
            it.sourceFilePath = oldFile.path;
            it.provider = imageData.value?.provider;
            //生成裁剪后的图片地址
            createCuttingFile();
            tempFile?.let {it1->
                it.cutFilePath = it1.path;
                setVMData(it);
            }
        }
    }

    /**
     * 创建裁剪后的图片文件
     * create by Administrator at 2020/9/30 13:50
     * @author Administrator
     * @return
     *      void
     */
    private fun createCuttingFile(){
        var outPath = "${FileUtil.getDiskPath()}/"
        if(imageData.value?.rootPath == null){
            outPath = "${outPath}/eastevil"
        }else{
            if(imageData.value?.rootPath!!.isNotNullOrEmpty()){
                outPath = "${outPath}/${imageData.value?.rootPath}"
            }
        }
        if(imageData.value?.childPath == null){
            outPath = "${outPath}/evimgs/cutting"
        }else{
            if(imageData.value?.childPath!!.isNotNullOrEmpty()){
                outPath = "${outPath}/${imageData.value?.childPath}/cutting"
            }else{
                outPath = "${outPath}/evimgs/cutting"
            }
        }
        FileUtil.createDirectory(outPath);
        outPath = "${outPath}/${FileUtil.createTempFileName()}"
        tempFile = FileUtil.createFile(outPath);
    }
}