package com.fish.staticlistview.staticlistview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.content.res.TypedArray;
import com.fish.staticlistview.R;

/**
 * 静态的listview，本质上一个LinearLayout，垂直布局，从上到下是，head，item1，item2，item3
 * 适合item数量较少（小于10）的时候使用
 * 比listview简单很多
 *
 * @see StaticListAdapter
 * Created by fish on 16/1/12.
 */
public class StaticListView extends LinearLayout {


    public void setAdapter(StaticListAdapter adapter) {
        this.adapter = adapter;
        adapter.setListView(this);
        adapter.initView();
    }

    private boolean showBottomLine;

    public StaticListAdapter getAdapter() {
        return adapter;
    }

    private StaticListAdapter adapter;


    public boolean isShowBottomLine() {
        return showBottomLine;
    }



    public StaticListView(Context context) {
        super(context);
        init(null);
    }

    public StaticListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StaticListView);
        showBottomLine = a.getBoolean(R.styleable.StaticListView_bottom_line, true);
        a.recycle();

    }


    public boolean hasHead() {
        return adapter.hasHead();
    }

    //获取itemview，不包括head,position从0开始
    public View getItemView(int position) {
        int start = hasHead() ? 1 : 0;
        return getChildAt(start + position);
    }

    public int geItemCount() {
        if (adapter != null) {
            return adapter.getItemCount();
        }
        return 0;
    }
}