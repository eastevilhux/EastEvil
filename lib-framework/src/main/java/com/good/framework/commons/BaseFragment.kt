package com.good.framework.commons

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.god.uikit.widget.TitleLayout
import com.god.uikit.widget.ViewToast
import com.good.framework.R
import com.good.framework.entity.Error
import com.good.framework.entity.ErrorType
import com.good.framework.entity.VMData

abstract open class BaseFragment<D : ViewDataBinding,V : EastViewModel<*>> : Fragment(),TitleLayout.OnTitleListener{
    val TAG = "BaseFragment=>";
    lateinit var viewModel : V;
    lateinit var dataBinding : D;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,getLayoutRes(),container,false);
        return dataBinding!!.root;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d(TAG,"onActivityCreated");

        var vp = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application));
        viewModel = vp.get(getVMClass()!!);
        viewModel.setLifecycleOwner(this);
        lifecycle.addObserver(viewModel);
        dataBinding.lifecycleOwner = this;
        initView()
        viewModel.initOnFragmentActivityCreate();
        viewModel?.error.observe(this, Observer {
            when(it.type){
                ErrorType.ERROR_LOGIN->{
                    loginError();
                }
                ErrorType.ERROR_UNKNOW->{
                    reqeustError(it);
                }
                else->{
                    showToastShort(it.msg?:getString(R.string.error_unknow));
                }
            }
        })

        viewModel.vmData.value?.let {
            vmDateChanged(it);
        }

        viewModel.loading.observe(this, Observer {
            if(it){
                getBaseActivity()?.showLoading();
            }else{
                getBaseActivity()?.dismissLoading();
            }
        })
    }

    abstract fun getLayoutRes():Int;

    abstract fun getVMClass() : Class<V>;

    open fun initView(){

    }

    open fun loginError(){

    }

    override fun onMenu() {

    }

    open fun reqeustError(error:Error){

    }

    open fun vmDateChanged(vmData: VMData){

    }

    fun getBaseActivity() : BaseActivity<*,*>?{
        if(activity is BaseActivity<*,*>){
            return activity as BaseActivity<*, *>;
        }
        return null;
    }


    fun showToastShort(@StringRes strRes:Int){
        ViewToast.show(activity!!,strRes, Toast.LENGTH_SHORT);
    }

    fun showToastShort(strRes:String){
        ViewToast.show(activity!!,strRes,Toast.LENGTH_SHORT);
    }

    fun showToastLong(@StringRes strRes:Int){
        ViewToast.show(activity!!,strRes,Toast.LENGTH_LONG);
    }

    fun showToastLong( strRes:String){
        ViewToast.show(activity!!,strRes,Toast.LENGTH_LONG);
    }

    override fun onBack() {
        var activity = activity as BaseActivity<*,*>;
        activity?.back();
    }

    override fun onStop() {
        super.onStop()
        viewModel?.onStop();
    }

    override fun onResume() {
        super.onResume()
        viewModel?.onResume();
    }

    override fun onPause() {
        super.onPause()
        viewModel?.onPause();
    }

    override fun onStart() {
        super.onStart()
        viewModel?.onStart();
    }

}