package com.fish.staticlistview.staticlistview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.fish.staticlistview.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 静态的listView
 *
 * @see StaticListView
 * 一般流程，setListerner,updateData/addOneData
 * Created by fish on 16/2/19.
 */
public abstract class StaticListAdapter<T> {

    private static final String TAG = "StaticListAdapter";

    public Resources getResources() {
        return resources;
    }

    private Resources resources = ApplicationUtil.getApplication().getResources();

    public static abstract class OnItemCickListerner {
        public abstract void onItemClick(Object object);
    }

    public void setOnItemClickListerner(OnItemCickListerner listerner1) {
        listerner = listerner1;
    }

    protected OnItemCickListerner listerner;

    //默认放5个item
    protected static final int DEFAULT_ITEM_SIZE = 5;

    //item的最大容量
    protected int capacity = DEFAULT_ITEM_SIZE;

    protected int resHead;
    protected int resItem;
    //尾部view的个数，一般来说只有bottomLine
    protected int tailNum = 0;

    //实际数据
    protected List<T> data = new ArrayList<T>();

    protected StaticListView rootView;
    private Context context;
    private View headView;

    public void setDefaultCapacity(int cap) {
        capacity = cap;
    }


    //更新全部数据，如果和旧数据一样就不用更新哦
    public void updateData(List<T> dataNew) {
        if (dataNew == null || dataNew.size() == 0) {
            //无新数据
            data.clear();
            rootView.setVisibility(View.GONE);
        } else {
            //有新数据进来
            rootView.setVisibility(View.VISIBLE);
            if (dataNew.size() <= capacity) {
                //重用view，不用扩充
                if (!isDataEqual(dataNew, data)) {
                    //数据是否一样，如果一样那什么都不用做，如果不一样，进行处理
                    Log.d(TAG, "StaticListAdapter " + "data diff");
                    data.clear();
                    data.addAll(dataNew);
                    setViewValue();
                }

            } else {
                //扩充view
                Log.d(TAG, "add view from " + capacity + " to " + dataNew.size());
                data.clear();
                data.addAll(dataNew);

                for (int i = 0; i < data.size() - capacity; i++) {
                    View view = LayoutInflater.from(getContext()).inflate(resItem, null);
                    rootView.addView(view, rootView.getChildCount() - tailNum);
                }

                capacity = data.size();
                setViewValue();
            }

        }

    }

    public void addOneData(int pos, T newData) {
        //数据更新
        if (data.isEmpty()) {
            rootView.setVisibility(View.VISIBLE);
        }
        data.add(pos, newData);

        //界面更新
        View view = LayoutInflater.from(getContext()).inflate(resItem, null);
        rootView.addView(view, pos + 1);
        setItemView(view, newData);

        if (data.size() > capacity) {
            capacity = data.size();
        }
    }

    public void removeOneData(int pos) {
        data.remove(pos);
        rootView.removeViewAt(pos + 1);
        capacity--;
        if (data.isEmpty()) {
            rootView.setVisibility(View.GONE);
        }
    }


    public StaticListAdapter(Context context, int resHead, int resItem) {
        this.context = context;
        this.resHead = resHead;
        this.resItem = resItem;
    }

    public StaticListAdapter(Context context, int resItem) {
        this(context, 0, resItem);
    }


    public void setListView(StaticListView listView) {
        this.rootView = listView;
    }

    public StaticListView getRootView() {
        return rootView;
    }


    protected Context getContext() {
        return context;
    }

    public View getHeadView() {
        return headView;
    }



    //初始化head，并且添加n个item进去
    public void initView() {

        if (resHead != 0) {
            headView = LayoutInflater.from(getContext()).inflate(resHead, rootView, false);
            rootView.addView(headView);
        }
        for (int i = 0; i < capacity; i++) {
            View view = LayoutInflater.from(getContext()).inflate(resItem, null);
            view.setVisibility(View.GONE);
            rootView.addView(view);
        }

        if (rootView.isShowBottomLine()) {
            addBottomLine();
        }

    }

    /**
     * 增加尾部的线
     */
    private void addBottomLine() {
        View viewLine = new View(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        viewLine.setBackgroundColor(Color.parseColor("#d9d9d9"));
        viewLine.setLayoutParams(params);
        rootView.addView(viewLine);
        tailNum++;

    }


    protected void setViewValue() {
        //有个head，所以加1
        int offset = hasHead() ? 1 : 0;
        for (int i = 0; i < data.size(); i++) {
            setItemView(rootView.getChildAt(i + offset), data.get(i));
            rootView.getChildAt(i + offset).setVisibility(View.VISIBLE);
        }
        for (int i = data.size() + offset; i < rootView.getChildCount() - tailNum; i++) {
            rootView.getChildAt(i).setVisibility(View.GONE);
        }
    }

    protected void setItemView(View view, final T object) {
        if (listerner != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listerner.onItemClick(object);
                }
            });
        }
        bindData(view, object);
    }

    protected abstract void bindData(View view, T object);

    protected boolean isDataEqual(List<T> data1, List<T> data2) {
        return false;
    }

    public boolean hasHead() {
        return resHead != 0;
    }

    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }


    public T getItemObject(int pos) {
        if (data != null) {
            return data.get(pos);
        }
        return null;
    }

    public int getPos(T oo) {
        return data.indexOf(oo);
    }
}
