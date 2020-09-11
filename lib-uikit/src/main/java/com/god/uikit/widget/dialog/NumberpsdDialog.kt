package com.god.uikit.widget.dialog

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.god.uikit.R
import com.god.uikit.databinding.DialogNumpasswordBinding
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.dip2Px
import com.god.uikit.widget.EastCodeView
import com.god.uikit.widget.ViewToast

class NumberpsdDialog private constructor(context: Context) : Dialog(context, R.style.DialogStyle),
    EastCodeView.InputOverListener {

    private lateinit var dataBinding : DialogNumpasswordBinding;
    private lateinit var myContent : Context;

    private val title = ObservableField<String>();
    private val amount = ObservableField<String>();
    private val typeText = ObservableField<String>();
    private var psdNumber = 6;

    init {
        myContent = context;
        title.set(myContent.getString(R.string.money_dialog_title))
        amount.set("0.00");
        typeText.set(myContent.getString(R.string.money_dialog_type))
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_numpassword,
            null,false);
        dataBinding.titleText = title;
        dataBinding.amount = amount;
        dataBinding.moneyType = typeText;
        dataBinding.psdnumber = psdNumber;

        setContentView(dataBinding.root)


        val dialogWindow = window
        val lp = dialogWindow!!.attributes

        lp.width = ViewUtil.getScreenSize(context)[0] - 100.dip2Px();
        lp.height = ViewUtil.dip2px(context, 200);
        //lp.alpha = 0.7f; // 透明度
        //lp.alpha = 0.7f; // 透明度
        dialogWindow!!.attributes = lp

        dataBinding.passwordView.setmInputOverListener(this)
    }


    override fun InputHint(string: String?) {
        ViewToast.show(myContent,string?:"A",Toast.LENGTH_SHORT)
    }

    override fun InputOver(string: String?) {
        ViewToast.show(myContent,string?:"H",Toast.LENGTH_SHORT)
    }



}