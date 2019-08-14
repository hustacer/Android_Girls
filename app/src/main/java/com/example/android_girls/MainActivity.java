package com.example.android_girls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<GirlBean> mGirlBeanList;
    private GirlsAdapter mGirlsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recylerview);

        initDataAndView();
    }

    private void initDataAndView() {
        mGirlBeanList = new ArrayList<GirlBean>();
        GirlBean girlBean = null;
        for (int i = 1; i <= 10; i++) {
            girlBean = new GirlBean();
            girlBean.setTitle("aaa" + i);
        }

        mGirlsAdapter = new GirlsAdapter(MainActivity.this, mGirlBeanList);
        mRecyclerView.setAdapter(mGirlsAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mGirlsAdapter.setOnItemActionListener(new GirlsAdapter.OnItemActionListener() {
            @Override
            public boolean onItemLongClickListener(View v, int pos) {
                Toast.makeText(MainActivity.this, "-长按-" + pos, Toast.LENGTH_SHORT).show();
                return false;

            }

            @Override
            public void onItemClickListener(View v, int pos) {
                Toast.makeText(MainActivity.this, "-单击-" + pos, Toast.LENGTH_SHORT).show();
            }

        });

    }
}
