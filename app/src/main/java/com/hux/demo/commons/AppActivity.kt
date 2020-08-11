package com.hux.demo.commons

import androidx.databinding.ViewDataBinding
import com.good.framework.commons.BaseActivity

abstract class AppActivity<D: ViewDataBinding,V : AppViewModel<*>> : BaseActivity<D, V>() {

    
}