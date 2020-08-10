package com.god.uikit.commons;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.god.uikit.presenter.BasePresenter;

import java.lang.reflect.Type;
import java.util.List;

/**
 * TO DO
 * createTime : 2018/4/8 22:22
 * update by admin on 2018/4/8.
 * version : $VARIABLE_NAME
 *
 * @see
 */

public abstract class BaseListAdapter<D extends ViewDataBinding,T> extends BaseAdapter {

    public D dataBinding;

    private List<T> list;

    private Context context;

    private LayoutInflater inflater;

    private  int variableId;

    public BaseListAdapter(List<T> list, Context context, int variableId){
        this.list = list;
        this.context = context;
        this.variableId = variableId;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            dataBinding  = DataBindingUtil.inflate(inflater,getLayoutRes(),parent,false);
            setViewParams(dataBinding.getRoot());
            setViewParams(dataBinding.getRoot(),position);
        }else{
            dataBinding = DataBindingUtil.getBinding(view);
        }
        setBean(list.get(position));
        setPresenter(dataBinding);
        setVariableId(dataBinding,position);
        dataBinding.setVariable(variableId,list.get(position));
        return dataBinding.getRoot();
    }

    public abstract  @LayoutRes
    int getLayoutRes();

    public abstract void setBean(T t);

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    public void setViewParams(View view){

    }

    public void setViewParams(View view,int position){

    }

    public void setPresenter(D dataBinding){

    }

    public void setVariableId(D dataBinding,int postion){

    }


    public Context getContext() {
        return context;
    }


}
