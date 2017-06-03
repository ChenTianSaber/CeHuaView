package com.example.chentian.cehuademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Integer> lists = new ArrayList<>();
    private MyRecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initviews();
    }

    private void initviews() {
        recyclerView = (MyRecyclerView) findViewById(R.id.my_recycler);
        adapter = new RecyclerAdapter(getApplicationContext(), lists);
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        for (int i = 1; i < 20; i++) {
            lists.add(i);
        }
    }
}
