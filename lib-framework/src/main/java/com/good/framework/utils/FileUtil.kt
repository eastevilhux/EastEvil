package com.good.framework.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import com.good.framework.commons.createRandomNumber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random


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

        //想要兼容全部版本，只要写个版本判断再根据需要调用就行
        fun uriToFile(context: Context, uri: Uri) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                uriToFileQ(context, uri)
            } else uriToFileN(context, uri)



        //android9以下的写法
        private fun uriToFileN(context: Context,uri: Uri): File {
            val img_path: String?
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            val actualimagecursor: Cursor? = context.contentResolver.query(
                uri, proj, null,
                null, null
            )
            img_path = if (actualimagecursor == null) {
                uri.path
            } else {
                val actual_image_column_index: Int = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                actualimagecursor.moveToFirst()
                actualimagecursor
                    .getString(actual_image_column_index)
            }
            return File(img_path)
        }


        private fun getFileFromUri(
            context: Context, uri: Uri, selection: String? = null,
            selectionArgs: kotlin.Array<String>? = null
        ): File? =
            context.contentResolver.query(uri, arrayOf("_data"), selection, selectionArgs, null)?.let {
                if (it.moveToFirst()) {
                    it.getColumnIndex(MediaStore.Images.Media.DATA).let { index->
                        if(index == -1 ) null else File(it.getString(index))
                    }
                } else null
            }



        @RequiresApi(Build.VERSION_CODES.Q)
        private fun uriToFileQ(context: Context, uri: Uri): File? =
            if (uri.scheme == ContentResolver.SCHEME_FILE)
                File(requireNotNull(uri.path))
            else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                //把文件保存到沙盒
                val contentResolver = context.contentResolver
                val displayName = run {
                    val cursor = contentResolver.query(uri, null, null, null, null)
                    cursor?.let {
                        if(it.moveToFirst())
                            it.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        else null
                    }
                }?:"${System.currentTimeMillis()}${Random.nextInt(0, 9999)}.${MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(uri))}"

                val ios = contentResolver.openInputStream(uri)
                if (ios != null) {
                    File("${context.externalCacheDir!!.absolutePath}/$displayName")
                        .apply {
                            val fos = FileOutputStream(this)
                            FileUtils.copy(ios, fos)
                            fos.close()
                            ios.close()
                        }
                } else null
            } else null
    }
}