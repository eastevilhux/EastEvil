package com.hux.demo.model.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hux.demo.R
import com.hux.demo.commons.AppActivity
import com.hux.demo.databinding.ActivityMainBinding

class MainActivity : AppActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_main;

    override fun getVMClass(): Class<MainViewModel> = MainViewModel::class.java;

    override fun initView() {

    }

}