package com.god.uikit.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.god.uikit.R
import com.god.uikit.databinding.LayoutTabmenuBinding
import com.god.uikit.utils.dip2Px

class TabmenuLayout : FrameLayout{

    companion object{
        const val TAG = "TabmenuLayout";
        const val LINE_WIDTH_DEFAULT = 50;
        const val LINE_HEIGHT_DEFAULT = 2;
        const val MARGIN_BOTTOM_DEFAULT = 5;
        const val GONE = 1;
        const val INVISIBLE = 2;
    }

    private var dataBinding : LayoutTabmenuBinding? = null;

    private val menuText = ObservableField<String>();
    private var lineState : Boolean = true;
    private var lineColor : Int = Color.WHITE;
    private var menuColor : Int = Color.WHITE;
    private var lineWidth : Int = ConstraintLayout.LayoutParams.WRAP_CONTENT;
    private var lineHeight : Int = ConstraintLayout.LayoutParams.WRAP_CONTENT;
    private var lineMarginBottom : Int = MARGIN_BOTTOM_DEFAULT.dip2Px()

    private var hiddenType : Int = 1;

    constructor(context: Context) : super(context) {

    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        initAttr(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initAttr(attrs)
    }


    private fun initAttr(@Nullable attrs : AttributeSet){
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TabmenuLayout);
        menuText.set(ta.getString(R.styleable.TabmenuLayout_menuText));
        lineState = ta.getBoolean(R.styleable.TabmenuLayout_lineState,true);
        menuColor = ta.getColor(R.styleable.TabmenuLayout_menuTextColor,Color.WHITE);
        lineColor = ta.getColor(R.styleable.TabmenuLayout_lineColor, Color.WHITE);
        lineWidth = ta.getDimensionPixelOffset(R.styleable.TabmenuLayout_line_width,LINE_WIDTH_DEFAULT.dip2Px());
        lineHeight = ta.getDimensionPixelOffset(R.styleable.TabmenuLayout_line_height,LINE_HEIGHT_DEFAULT.dip2Px());
        lineMarginBottom = ta.getDimensionPixelOffset(R.styleable.TabmenuLayout_lineMarginButtom,
            MARGIN_BOTTOM_DEFAULT.dip2Px());
        hiddenType = ta.getIndex(R.styleable.TabmenuLayout_hiddenType);
        ta.recycle();
        init();
    }

    @SuppressLint("WrongConstant")
    private fun init(){
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_tabmenu,
            this, true
        )
        dataBinding?.menuText = menuText;

        dataBinding?.tvItemtext!!.setTextColor(menuColor);
        dataBinding?.tvLine!!.setBackgroundColor(lineColor);

        if(lineState){
            //需要显示
            if(hiddenType == GONE){
                dataBinding?.tvLine!!.visibility = View.GONE;
            }else{
                dataBinding?.tvLine!!.visibility = INVISIBLE;
            }
        }else{
            dataBinding?.tvLine!!.visibility = View.VISIBLE;
        }

        var lineParams = dataBinding?.tvLine!!.layoutParams as MarginLayoutParams;
        lineParams.width = lineWidth;
        lineParams.height = lineHeight;
        lineParams.setMargins(0,0,0,lineMarginBottom);
        dataBinding?.tvLine!!.layoutParams = lineParams;
    }


    @SuppressLint("WrongConstant")
    fun setLineState(lineState:Boolean){
        this.lineState = lineState;
        if(lineState){
            //需要显示
            if(hiddenType == GONE){
                dataBinding?.tvLine!!.visibility = View.GONE;
            }else{
                dataBinding?.tvLine!!.visibility = INVISIBLE;
            }
        }else{
            dataBinding?.tvLine!!.visibility = View.VISIBLE;
        }
    }

    fun getLineState():Boolean{
        return lineState;
    }

    fun setMenuTextColor(color:Int){
        dataBinding?.tvItemtext?.setTextColor(color);
    }

    fun setMenuText(menuText : String){
        this.menuText.set(menuText);
    }

}