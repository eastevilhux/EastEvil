package com.god.uikit.commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2018/7/5.
 */

public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.GodViewHolder>{
    private static final String TAG = "RecyclerAdapter==>";
    public Map<Integer,Integer> bindMap;

    private LayoutInflater layoutInflater;

    private Context context;

    public RecyclerAdapter(Context context){
        bindMap = createBindMap();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        for(Map.Entry<Integer, Integer> entry:bindMap.entrySet()){
            Log.d(TAG,"key=>"+entry.getKey()+",value=>"+entry.getValue());
        }
    }

    @Override
    public GodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding inflate = DataBindingUtil.inflate(layoutInflater,viewType, parent, false);//引入布局
        return new GodViewHolder(inflate);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(GodViewHolder holder, int position) {
        int type = getItemViewType(position);
        Log.d(TAG,String.valueOf(type));
        Integer varid = bindMap.get(type);

        setView(holder.dataBinding.getRoot(),getItemViewType(position),position);

        setView(holder.dataBinding,getItemViewType(position),position);

        if(varid != null) {
            try {
                setBean(holder.dataBinding, varid, position);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        setViewBackground(holder.dataBinding.getRoot(),getItemViewType(position),position);
    }

    @Override
    public int getItemViewType(int position) {
        Set<Integer> integers = bindMap.keySet();
        return integers.iterator().next();
    }

    public abstract void setBean(ViewDataBinding dataBinding, int varid, int position) throws IllegalAccessException;

    /**
     * 生成布局资源id与布局中对用的BR id即variableid对应的Map集合,<br/>
     * Map集合的id为资源布局id，value为variableid
     * @author hux
     * @createTime 2018/7/5 10:41
     * @since 0.0.1
     * @return
     *      资源id与variableid对应的Map集合
     */
    public abstract Map<Integer,Integer> createBindMap();


    /**
     * 设置布局中的其他控件属性
     * @author hux
     * @createTime 2018/10/17 15:42
     * @since 1.0.0
     * @see RecyclerAdapter
     * @param rootView
     *      根布局
     * @param viewType
     *      view类型
     * @param position
     *      下标位置
     * @return
     *      void
     */
    public void setView(View rootView,int viewType,int position){
    }


    public void setViewBackground(View rootView,int viewType,int position){

    }


    /**
     * 设置布局中的其他控件属性
     * @author hux
     * @createTime 2018/10/17 15:42
     * @since 1.0.0
     * @see RecyclerAdapter
     * @param dataBinding
     *      {@link ViewDataBinding}
     * @param viewType
     *      view类型
     * @param position
     *      下标位置
     * @return
     *      void
     */
    public void setView(ViewDataBinding dataBinding,int viewType,int position){

    }

    public class GodViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding dataBinding;


        public GodViewHolder(ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.dataBinding = itemView;
        }
    }

    public Context getContext() {
        return context;
    }
}
