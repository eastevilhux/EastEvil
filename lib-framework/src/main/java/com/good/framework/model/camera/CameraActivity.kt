package com.good.framework.model.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.media.ImageReader.OnImageAvailableListener
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.screenSize
import com.god.uikit.widget.LoadingDialog
import com.god.uikit.widget.ViewToast
import com.good.framework.R
import com.good.framework.commons.BaseActivity
import com.good.framework.databinding.CameraActivityBinding
import com.good.framework.entity.VMData
import com.good.framework.model.uploadimg.UploadImgData
import com.good.framework.model.uploadimg.UploadImgData.Companion.CHILD_PATH_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_CUTHEIGHT_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_CUTWIDTH_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_HEIGHT_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.IMAGE_WIDTH_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.PROVINDER_KEY
import com.good.framework.model.uploadimg.UploadImgData.Companion.ROOT_PAHT_KEY
import java.util.*


class CameraActivity : BaseActivity<CameraActivityBinding, CameraViewModel>(){

    companion object{
        var ORIENTATIONS = SparseIntArray()
    }
    init {
        ORIENTATIONS.append(Surface.ROTATION_0, 90)
        ORIENTATIONS.append(Surface.ROTATION_90, 0)
        ORIENTATIONS.append(Surface.ROTATION_180, 270)
        ORIENTATIONS.append(Surface.ROTATION_270, 180)
    }

    private var mSurfaceHolder: SurfaceHolder? = null
    private var mCameraManager : CameraManager? = null//摄像头管理器
    private var childHandler: Handler? = null;
    private var mainHandler:Handler? = null
    private var mCameraID : String? = null//摄像头Id 0 为后  1 为前
    private var mImageReader: ImageReader? = null
    private var mCameraCaptureSession: CameraCaptureSession? = null
    private var mCameraDevice: CameraDevice? = null

    var dialog : LoadingDialog? = null;

    private var cameraWidth:Int = 0;
    private var cameraHeight :Int = 0;
    private var cutWidth:Int = 0;
    private var cutHeight :Int = 0;
    private var provider:String? = null;

    override fun getLayoutRes(): Int = R.layout.camera_activity;

    override fun getVMClass(): Class<CameraViewModel> = CameraViewModel::class.java;

    override fun initView() {
        dataBinding?.camerac = this;

        cameraWidth = intent?.getIntExtra(IMAGE_WIDTH_KEY,0)?:0;
        cameraHeight = intent?.getIntExtra(IMAGE_HEIGHT_KEY,0)?:0;
        cutWidth = intent?.getIntExtra(IMAGE_CUTWIDTH_KEY,0)?:0;
        cutHeight = intent?.getIntExtra(IMAGE_CUTHEIGHT_KEY,0)?:0;
        provider = intent?.getStringExtra(PROVINDER_KEY);
        var rootPath = intent?.getStringExtra(ROOT_PAHT_KEY);
        var childPath = intent?.getStringExtra(CHILD_PATH_KEY);
        viewModel?.rootPath = rootPath;
        viewModel?.childPath = childPath;

        if(cameraWidth == 0 || cameraHeight == 0){
            var size = screenSize(this);
            if(cameraWidth == 0){
                cameraWidth = size[0];
            }
            if(cameraHeight == 0){
                cameraHeight = size[1];
            }
        }
        if(cutWidth == 0 || cutHeight == 0){
            var size = ViewUtil.getScreenSize(this);
            if(cutWidth == 0){
                cutWidth = size[0];
            }
            if(cutHeight == 0){
                cutHeight = cutWidth;
            }
        }
        dialog = LoadingDialog(this);
        mSurfaceHolder = dataBinding?.sfCamera!!.getHolder();
        mSurfaceHolder?.setKeepScreenOn(true);
        mSurfaceHolder?.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) { //SurfaceView创建
                // 初始化Camera
                initCamera()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) { //SurfaceView销毁
                // 释放Camera资源
                if (null != mCameraDevice) {
                    mCameraDevice!!.close()
                    this@CameraActivity.mCameraDevice = null
                }
            }
        })
        initCamera();
    }

    fun viewClick(view:View){
        takePicture();
    }

    private fun initCamera(){
        val handlerThread = HandlerThread("Camera2")
        handlerThread.start();
        childHandler = Handler(handlerThread.getLooper());
        mainHandler = Handler(getMainLooper());
        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;//后摄像头

        mImageReader = ImageReader.newInstance(cameraWidth, cameraHeight, ImageFormat.JPEG,1);
        mImageReader?.setOnImageAvailableListener(OnImageAvailableListener { reader ->

            //可以在这里处理拍照得到的临时照片 例如，写入本地
            mCameraDevice!!.close()
            // 拿到拍照照片数据
            val image: Image = reader.acquireNextImage()
            dialog?.show();
            viewModel?.saveImage(image);

        }, mainHandler)
        //获取摄像头管理

        //获取摄像头管理
        mCameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            //打开摄像头
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mCameraManager!!.openCamera(mCameraID!!, stateCallback, mainHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


    /**
     * 摄像头创建监听
     */
    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) { //打开摄像头
            mCameraDevice = camera
            //开启预览
            takePreview()
        }

        override fun onDisconnected(camera: CameraDevice) { //关闭摄像头
            if (null != mCameraDevice) {
                mCameraDevice!!.close()
                this@CameraActivity.mCameraDevice = null
            }
        }

        override fun onError(camera: CameraDevice, error: Int) { //发生错误
            Toast.makeText(this@CameraActivity, "摄像头开启失败", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 开始预览
     */
    private fun takePreview() {
        try {
            // 创建预览需要的CaptureRequest.Builder
            val previewRequestBuilder =
                mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            previewRequestBuilder.addTarget(mSurfaceHolder!!.surface)
            // 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            mCameraDevice!!.createCaptureSession(
                Arrays.asList(
                    mSurfaceHolder!!.surface,
                    mImageReader!!.surface
                ), object : CameraCaptureSession.StateCallback( // ③
                ) {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        if (null == mCameraDevice) return
                        // 当摄像头已经准备好时，开始显示预览
                        mCameraCaptureSession = cameraCaptureSession
                        try {
                            // 自动对焦
                            previewRequestBuilder.set(
                                CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                            )
                            // 打开闪光灯
                            previewRequestBuilder.set(
                                CaptureRequest.CONTROL_AE_MODE,
                                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                            )
                            // 显示预览
                            val previewRequest = previewRequestBuilder.build()
                            mCameraCaptureSession!!.setRepeatingRequest(
                                previewRequest,
                                null,
                                childHandler
                            )
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                        Toast.makeText(this@CameraActivity, "配置失败", Toast.LENGTH_SHORT).show()
                    }
                }, childHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


    /**
     * 拍照
     */
    private fun takePicture() {
        if (mCameraDevice == null) return
        // 创建拍照需要的CaptureRequest.Builder
        val captureRequestBuilder: CaptureRequest.Builder
        try {
            captureRequestBuilder =
                mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            captureRequestBuilder.addTarget(mImageReader!!.surface)
            // 自动对焦
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
            )
            // 自动曝光
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
            )
            // 获取手机方向
            val rotation = windowManager.defaultDisplay.rotation
            // 根据设备方向计算设置照片的方向
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))
            //拍照
            val mCaptureRequest = captureRequestBuilder.build()
            mCameraCaptureSession!!.capture(mCaptureRequest, null, childHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun vmDataChange(data: VMData) {
        super.vmDataChange(data)
        when(data.code){
            VMData.Code.CODE_SUCCESS->{
                dialog?.dismiss();
                viewModel?.filePath?.let {
                    var intent = Intent(this,ImageActivity::class.java);
                    intent.putExtra(UploadImgData.FILE_PATH_KEY,it);
                    intent.putExtra(IMAGE_CUTWIDTH_KEY,cutWidth);
                    intent.putExtra(IMAGE_CUTHEIGHT_KEY,cutHeight);
                    intent.putExtra(PROVINDER_KEY,provider);
                    intent.putExtra(ROOT_PAHT_KEY,viewModel?.rootPath);
                    intent.putExtra(CHILD_PATH_KEY,viewModel?.childPath);
                    startActivity(intent)
                    finish();
                    mCameraDevice?.close();
                    mCameraDevice = null;
                }?:let{
                    ViewToast.show(this,data.msg?:getString(R.string.error_save_image),Toast.LENGTH_LONG);
                }
            }
            VMData.Code.CODE_SHOW_MSG->{
                dialog?.dismiss();
                ViewToast.show(this,data.msg?:getString(R.string.error_save_image),Toast.LENGTH_LONG);
            }
        }
    }

}
