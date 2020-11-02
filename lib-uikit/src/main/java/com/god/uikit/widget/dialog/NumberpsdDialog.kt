package com.god.uikit.widget.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.god.uikit.R
import com.god.uikit.databinding.DialogNumpasswordBinding
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.dip2Px
import com.god.uikit.widget.EastCodeView
import com.god.uikit.widget.ViewToast
import java.lang.StringBuilder

class NumberpsdDialog private constructor(builder: Builder) : Dialog(builder.context, R.style.DialogStyle),
    EastCodeView.InputOverListener {

    private lateinit var dataBinding: DialogNumpasswordBinding;
    private lateinit var myContent: Context;

    private var onPasswordDialogListener : OnPasswordDialogListener? = null;

    private val title = ObservableField<String>();
    private val amount = ObservableField<String>();
    private val typeText = ObservableField<String>();
    private var psdNumber = 6;

    init {
        myContent = context;
        title.set(builder.title)
        amount.set(builder.amount);
        typeText.set(builder.typeText)
        psdNumber = builder.psdNumber;
        this.onPasswordDialogListener = builder.onPasswordDialogListener;
        dataBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.dialog_numpassword,
            null, false
        );
        dataBinding.dialog = this;
        dataBinding.titleText = title;
        dataBinding.amount = amount;
        dataBinding.moneyType = typeText;
        dataBinding.passwordView.setCount(psdNumber);

        setContentView(dataBinding.root)


        val dialogWindow = window
        val lp = dialogWindow!!.attributes

        lp.width = ViewUtil.getScreenSize(context)[0] - 100.dip2Px();
        lp.height = ViewUtil.dip2px(context, 200);
        //lp.alpha = 0.7f; // 透明度
        //lp.alpha = 0.7f; // 透明度
        dialogWindow!!.attributes = lp

        var size = lp.width - 20.dip2Px();
        size /= psdNumber;
        dataBinding.passwordView.setCodeWidth(size)
        dataBinding.passwordView.setCodeHeight(size);

        dataBinding.passwordView.setmInputOverListener(this)
    }

    fun onViewClick(view : View){
        when(view.id){
            R.id.iv_dismiss->{
                dismiss()
                if(onPasswordDialogListener != null){
                    onPasswordDialogListener!!.onDismiss();
                }
            }
        }
    }


    override fun InputHint(string: String?) {

    }

    override fun InputOver(string: String) {
        dismiss();
        if(onPasswordDialogListener != null)
            onPasswordDialogListener!!.onPassword(string);
    }

    fun setOnPasswordDialogListener(onPasswordDialogListener : OnPasswordDialogListener){
        this.onPasswordDialogListener = onPasswordDialogListener;
    }


    interface OnPasswordDialogListener{

        fun onPassword(password : String);

        fun onDismiss();
    }

    class Builder constructor(context: Context){
        var context : Context;
        var title : String? = null;
        var amount : String? = null;
        var typeText : String? = null;
        var psdNumber : Int = 6;
        var onPasswordDialogListener : OnPasswordDialogListener? = null;

        init {
            this.context = context;
        }

        fun title(title : String) : Builder{
            this.title = title;
            return this;
        }

        fun amount(amount: String) : Builder{
            this.amount = amount;
            return this;
        }

        fun typeText(typeText : String) : Builder{
            this.typeText = typeText;
            return this;
        }

        fun psdNumber(psdNumber : Int) : Builder{
            this.psdNumber = psdNumber;
            return this;
        }

        fun onPasswordDialogListener(onPasswordDialogListener : OnPasswordDialogListener) : Builder{
            this.onPasswordDialogListener = onPasswordDialogListener;
            return this;
        }

        fun build() : NumberpsdDialog{
            return NumberpsdDialog(this);
        }
    }

}