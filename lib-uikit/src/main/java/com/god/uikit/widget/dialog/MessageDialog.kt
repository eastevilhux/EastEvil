package com.god.uikit.widget.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.god.uikit.R
import com.god.uikit.databinding.DialogMessageBinding
import com.god.uikit.databinding.DialogNumpasswordBinding
import com.god.uikit.utils.ViewUtil
import com.god.uikit.utils.dip2Px

class MessageDialog private constructor(builder: Builder) : Dialog(builder.context, R.style.DialogStyle){
    private var tag : Int = 0;

    private val title = ObservableField<String>();
    private val message = ObservableField<String>();
    private val enterText = ObservableField<String>();
    private val cancelText = ObservableField<String>();


    private lateinit var dataBinding: DialogMessageBinding;
    private lateinit var myContent: Context;

    private var onDialogListener : OnDialogListener? = null;

    init {
        myContent = builder.context;
        title.set(builder.title);
        message.set(builder.message);
        enterText.set(builder.enterText);
        cancelText.set(builder.cancelText);
        onDialogListener = builder.onDialogListener;
        dataBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.dialog_message, null, false
        );
        dataBinding.dialog = this;
        dataBinding.title = title;
        dataBinding.message = message;
        dataBinding.enterText = enterText;
        dataBinding.cancelText = cancelText;

        setContentView(dataBinding.root)

        val dialogWindow = window
        val lp = dialogWindow!!.attributes

        lp.width = ViewUtil.getScreenSize(context)[0] - 100.dip2Px();
        lp.height = ViewUtil.dip2px(context, 150);
        //lp.alpha = 0.7f; // 透明度
        //lp.alpha = 0.7f; // 透明度
        dialogWindow!!.attributes = lp

    }

    fun onViewClick(view : View){
        when(view.id){
            R.id.bt_submit->{
                if(onDialogListener != null){
                    onDialogListener!!.onEnter(tag);
                }
            }
            R.id.bt_cancel->{
                dismiss();
                if(onDialogListener != null)
                    onDialogListener!!.onCancel();
            }
        }
    }

    fun setTag(tag : Int){
        this.tag = tag;
    }

    interface OnDialogListener{

        fun onCancel();

        fun onEnter(tag : Int);
    }


    class Builder constructor(context: Context){
        var context : Context;
        var title : String? = null;
        var message : String? = null;
        var enterText : String? = null;
        var cancelText : String? = null;
        var onDialogListener : OnDialogListener? = null;

        init {
            this.context = context;
        }


        fun title(title : String) : Builder {
            this.title = title;
            return this;
        }

        fun message(message : String) : Builder{
            this.message = message;
            return this;
        }

        fun enterText(enterText : String): Builder {
            this.enterText = enterText;
            return this;
        }

        fun cancelText(cancelText : String): Builder {
            this.cancelText = cancelText;
            return this;
        }

        fun btnText(enterText : String,cancelText : String): Builder {
            this.enterText = enterText;
            this.cancelText = cancelText;
            return this;
        }

        fun onDialogListener(onDialogListener : OnDialogListener): Builder {
            this.onDialogListener = onDialogListener;
            return this;
        }

        fun build() : MessageDialog {
            return MessageDialog(this);
        }
    }

}