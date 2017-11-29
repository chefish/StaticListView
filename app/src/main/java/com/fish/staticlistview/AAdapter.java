package com.fish.staticlistview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fish.staticlistview.staticlistview.StaticListAdapter;

/**
 * Created by fish on 2017/11/23.
 * yuxm_zju@aliyun.com
 */

public class AAdapter extends StaticListAdapter<Fruit> {
    public AAdapter(Context context) {
        super(context, 0, R.layout.aaa);
        //默认设置4条
//        setDefaultCapacity(4);
    }


    @Override
    protected void bindData(View view, Fruit object) {
        TextView tv = view.findViewById(R.id.aa);
        tv.setText(object.name);
    }


}
