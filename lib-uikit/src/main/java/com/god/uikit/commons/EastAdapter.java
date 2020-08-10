package com.god.uikit.commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.databinding.ViewDataBinding;

import com.god.uikit.BR;
import com.god.uikit.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class EastAdapter<T> extends RecyclerAdapter{
    private static final String TAG = "EastAdapter==>";

    private List<T> dataList;

    private State state;

    public EastAdapter(Context context) {
        super(context);
        state = State.STATE_NO_MORE;
    }

    public EastAdapter(Context context,List<T> dataList){
        super(context);
        this.dataList = dataList;
        state = State.STATE_NO_MORE;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : isHaveFoot()? dataList.size()+1 : dataList.size();
    }

    @Override
    public void setBean(ViewDataBinding dataBinding, int varid, int position) throws IllegalAccessException {
        if(isHaveFoot()){
            if(position >= dataList.size()){
                Log.d(TAG+"foot==>",String.valueOf(position)+",size==>"+dataList.size()+",state=>"+state);
                switch (state){
                    case STATE_LOAD_MORE:
                        if(isHaveMoreLoad()){
                            if(disposeMoreLoad()){
                                dataBinding.setVariable(BR.loadMore,getContext().getString(R.string.lib_load_more));
                            }else{
                                setLoadMoreView(dataBinding);
                            }
                        }
                        break;
                    case STATE_NO_MORE:
                        if(isHaveNomore()){
                            if(disposeNomore()){
                                dataBinding.setVariable(BR.nomore,getContext().getString(R.string.lib_nomore));
                            }else{
                                setNomoreView(dataBinding);
                            }
                        }
                        break;
                    case STATE_LOADING:
                        if(isHaveLoading()){
                            if(disposeLoading()){
                                dataBinding.setVariable(BR.laoding,getContext().getString(R.string.lib_loading));
                            }else{
                                setLoadingView(dataBinding);
                            }
                        }
                        break;
                    default:
                        throw new IllegalAccessException("unknow state now when to set bean info");
                }
            }else{
                dataBinding.setVariable(varid,dataList.get(position));
            }
        }else{
            dataBinding.setVariable(varid,dataList.get(position));
        }

    }

    public boolean isHaveFoot(){
        return false;
    }

    public boolean isHaveMoreLoad(){
        return false;
    }

    public boolean isHaveLoading(){
        return false;
    }

    public boolean isHaveNomore(){
        return false;
    }

    public boolean disposeMoreLoad(){
        return true;
    }

    public boolean disposeLoading(){
        return true;
    }

    public boolean disposeNomore(){
        return true;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


    @Override
    public Map<Integer, Integer> createBindMap() {
        try {
            return buildInitMap();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Map<Integer, Integer> buildInitMap() throws IllegalAccessException {
        HashMap<Integer,Integer> map = new HashMap<>();
        if(isHaveFoot()){
            if(isHaveLoading()) {
                int[] foot = getLoadingType();
                if (foot == null || foot.length != 2) {
                    throw new IllegalAccessException("loading view type not find");
                }
                map.put(foot[0], foot[1]);
            }
            if(isHaveNomore()){
                int[] nodata = getNodataType();
                if(nodata == null || nodata.length != 2){
                    throw new IllegalAccessException("nodata view type not find");
                }
                map.put(nodata[0],nodata[1]);
            }
            if(isHaveMoreLoad()){
                int[] moredata = getLoadMore();
                if(moredata == null || moredata.length != 2){
                    throw new IllegalAccessException("load more data view type not find");
                }
            }
        }
        bindItem(map);
        return map;
    }

    @SuppressLint("LongLogTag")
    @Override
    public int getItemViewType(int position) {
        if(isHaveFoot()){
            if(position == dataList.size()){
                switch (state){
                    case STATE_LOADING:
                        Log.d(TAG+"STATE=>","STATE_LOADING");
                        return getLoadingType()[0];
                    case STATE_NO_MORE:
                        Log.d(TAG+"STATE=>","STATE_NO_MORE");
                        return getNodataType()[0];
                    case STATE_LOAD_MORE:
                        Log.d(TAG+"STATE=>","STATE_LOAD_MORE");
                        return getLoadMore()[0];
                    default:
                        try {
                            throw new IllegalAccessException("no view type fond");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return 0;
                }
            }else{
                return getViewItemType(position);
            }
        }else{
            return getViewItemType(position);
        }
    }

    protected abstract int getViewItemType(int position);

    abstract protected void bindItem(Map<Integer,Integer> map);

    public int[] getLoadingType(){
        return new int[]{R.layout.foot_list_loading,BR.laoding};
    }

    public int[] getNodataType(){
        return new int[]{R.layout.nomore_list_item,BR.nomore};
    }

    public int[] getLoadMore(){
        return new int[]{R.layout.foot_load_more,BR.loadMore};
    }

    public void setLoadingView(ViewDataBinding dataBinding){

    }

    public void setNomoreView(ViewDataBinding dataBinding){

    }

    public void setLoadMoreView(ViewDataBinding dataBinding){

    }

    public static enum State{
        STATE_LOADING(0x01),
        STATE_LOAD_MORE(0x02),
        STATE_NO_MORE(0x03);

        private int state;
        private State(int state){
            this.state = state;
        }
    }


}
