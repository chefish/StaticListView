
package com.fish.staticlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fish.staticlistview.staticlistview.StaticListAdapter;
import com.fish.staticlistview.staticlistview.StaticListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StaticListView staticListView = (StaticListView) findViewById(R.id.static_list);
        StaticListAdapter adapter = new AAdapter(this);
        staticListView.setAdapter(adapter);
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit("apple"));
        fruitList.add(new Fruit("banana"));
        fruitList.add(new Fruit("pitaya"));
        fruitList.add(new Fruit("orange"));
        adapter.updateData(fruitList);
    }

}
