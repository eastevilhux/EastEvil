package com.good.framework.commons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.auction.framework.utils.AutoDisposeUtil
import com.uber.autodispose.AutoDisposeConverter

abstract class BaseViewModel(application: Application) : AndroidViewModel(application),ILifecycleObserver{
    lateinit var mLifecycleOwner :LifecycleOwner;

    override fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
    }

    override fun onCreate(owner: LifecycleOwner?) {
    }

    override fun onDestroy(owner: LifecycleOwner?) {
    }

    override fun onLifecycleChange(owner: LifecycleOwner?) {
    }

    protected fun <T> bindLifecycle() : AutoDisposeConverter<T> {
        return AutoDisposeUtil.bindLifecycle(mLifecycleOwner!!)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop() {
    }

}