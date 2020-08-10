package com.good.framework.model.uploadimg

import com.good.framework.entity.VMData

class UploadImgData :VMData(){

    enum class ImageType(private val type: Int){
        TYPE_ERROR(0),
        TYPE_SPLASH(0x01),
        TYPE_HOME_BANNER(0x02);

        fun getType(): Int {
            return type;
        }

        companion object{
            fun getType(type:Int) : ImageType{
                return when(type){
                    0->TYPE_ERROR
                    0x01->TYPE_SPLASH
                    0x02->TYPE_HOME_BANNER
                    else->TYPE_ERROR;
                }
            }
        }
    }

    enum class AppType(private val type:Int){
        APP_ERROR(0),
        APP_LIFEHOUSE(0x01),
        APP_LIFEMANAGER(0x02);

        fun getType(): Int {
            return type;
        }

        companion object{
            fun getType(type : Int): AppType {
                return when(type){
                    0->APP_ERROR
                    0x01->APP_LIFEHOUSE
                    0x02->APP_LIFEMANAGER
                    else->APP_ERROR
                }
            }
        }
    }

    companion object{
        const val IMAGE_TYPE_KEY : String = "uploadImgImageType";
        const val APP_TYPE_KEY : String = "upload_img_app_type";
        const val ICON_FLAG_KEY : String = "upload_have_icon";
        const val LOADING_FALG_KEY : String = "upload_img_isload";
        const val RELATION_ID_KEY : String = "relationId_key";
        const val IMAGE_WIDTH_KEY : String = "imageWidth_key";
        const val IMAGE_HEIGHT_KEY : String = "imageheight_key";
        const val IMAGE_FLAG_KEY : String = "image_falg_key";
        const val IMAGE_INFO_KEY : String = "image_info_key";
        const val FILE_PATH_KEY : String = "life_file_key";
        const val IMAGE_CUTWIDTH_KEY : String = "cut_width_key";
        const val IMAGE_CUTHEIGHT_KEY : String = "cut_height_key";
    }
}