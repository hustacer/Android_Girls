package com.example.android_girls;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<GirlBean> mGirlBeanList;
    private GirlsAdapter mGirlsAdapter;
//    private static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";
//    private static String URL = "https://api.ooopn.com/image/beauty/api.php?type=json";
    private static String URL = "http://api.tianapi.com/meinv/?key=cf89613c7575ae4673c4c9b1d2861ed4&num=10";
    private List<GirlBean> mGirlListTemp = new ArrayList<GirlBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recylerview);

        initDataAndView();
    }

    private void initDataAndView() {
//        for (int i = 0; i < 10; i++) {
//            new NewsAsyncTask().execute(URL);
//        }

        new NewsAsyncTask().execute(URL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        if(mRecyclerView.getRecycledViewPool()!=null){
            mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
        }

    }


    class NewsAsyncTask extends AsyncTask<String, Void, List<GirlBean>> {
        @Override
        protected List<GirlBean> doInBackground(String... strings) {
            return getJsonData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<GirlBean> girlBeans) {
            super.onPostExecute(girlBeans);
//
//            //-------------------------------------------
//            GirlBean girlBean;
//            for (int i = 0; i < girlBeans.size(); i++) {
//                girlBean = new GirlBean();
//                girlBean.title = "picture";
//                girlBean.img_url = girlBeans.get(i).img_url;
//                mGirlListTemp.add(girlBean);
//            }
//            //-------------------------------------------
//
//            mGirlsAdapter = new GirlsAdapter(MainActivity.this, mGirlListTemp);
            mGirlsAdapter = new GirlsAdapter(MainActivity.this, girlBeans);
            mGirlBeanList = girlBeans;
            mRecyclerView.setAdapter(mGirlsAdapter);

            mGirlsAdapter.setOnItemActionListener(new GirlsAdapter.OnItemActionListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(MainActivity.this, "-单击-" + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public boolean onItemLongClick(View view, int position) {
                    Toast.makeText(MainActivity.this, "-长按-" + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }

    private List<GirlBean> getJsonData(String url) {
        List<GirlBean> girlList = new ArrayList<GirlBean>();

        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            GirlBean girlBean;
            try {
                jsonObject =new JSONObject(jsonString);
                JSONArray jsonArray =jsonObject.getJSONArray("newslist");
//                JSONArray jsonArray =jsonObject.getJSONArray("data");
                for(int i = 0;i < jsonArray.length(); i++ ){
                    jsonObject = jsonArray.getJSONObject(i);
                    girlBean = new GirlBean();
                    girlBean.title =jsonObject.getString("title");
                    girlBean.img_url = jsonObject.getString("picUrl");
//                    girlBean.title =jsonObject.getString("description");
//                    girlBean.img_url = jsonObject.getString("picSmall");
                    girlList.add(girlBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return girlList;
    }

    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";

        String line ="";
        try {
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result +=line;
            }

            isr.close();
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
