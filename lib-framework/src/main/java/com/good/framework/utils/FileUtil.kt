package com.good.framework.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.good.framework.commons.createRandomNumber
import java.io.File
import java.io.IOException


class FileUtil private constructor(){

    companion object{


        fun getDiskPath() : String? {
            return  Environment.getExternalStorageDirectory().toString();
        }


        /**
         * 创建一个指定的标准目录
         * @author Administrator
         * createTime 2016/11/15 13:46
         * update by Administrator on 2016/11/15
         * @param dirPath
         * 目录路径
         */
        fun createDirectory(dirPath: String?) {
            val file = File(dirPath)
            if (!file.exists()) {
                file.mkdirs()
            }
        }

        /**
         * 创建一个新的指定路径的文件
         * @author Administrator
         * createTime 2016/11/15 13:50
         * update by hux on 2016/11/15
         * @param dirPath
         * 文件指定路径
         * @return
         * File 该地址对应的创建后的文件对象
         */
        fun createFile(dirPath: String?): File? {
            val file = File(dirPath)
            Log.d("FILE_TAG=>",dirPath);
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    Log.d("FILE_TAG==>",e.message);
                    e.printStackTrace()
                }
            }
            return file
        }


        /**
         * 创建临时文件名称
         * @author hux
         * @createTime 2018/6/5 17:41
         * @since 0.0.1
         * @return
         * name of file
         */
        fun createTempFileName(): String? {
            val sb =
                StringBuilder(TimeUtil.getCurrentTime("yyyy_MM_dd_HH_mm_ss"))
            sb.append(createRandomNumber(100, 999))
            sb.append(".png")
            return sb.toString()
        }
    }
}