package com.example.find.HomePage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.find.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import Adapter.HomePageListViewAdapter;

public class TestActivity extends AppCompatActivity {

    private final String TAG = "HomePageActivity";
    private ViewPager vp_activity;
    List<Bitmap> viewLists;//数据源
    private Context mContext; // 上下文
    private LayoutInflater mInflater; // 用于解XML
    private LinkedList<View> mViews; // 用于显示的View
    private ScheduledExecutorService scheduledExecutorService;

    ListView listView;
    ArrayList<Map<String,Object>> list;
    Map<String,Object> topicMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        vp_activity = (ViewPager)findViewById(R.id.vp_activity);
        listView =(ListView)findViewById(R.id.listview);

        list = getLists();
        topicMap = getTopicMap();
        viewLists = getViewLists();
        mViews = ViewPagerCycle(this,viewLists);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        HomePageListViewAdapter listviewAdapter = new HomePageListViewAdapter(this,list,topicMap,viewLists,mViews,scheduledExecutorService);
        listView.setAdapter(listviewAdapter);

    }

    public ArrayList<Map<String,Object>> getLists(){
        ArrayList<Map<String,Object>> listdata = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.bird1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.example6);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.mipmap.example7);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.mipmap.example8);
        for(int i=0;i<10;i++){
            map.put("head",bitmap1);
            map.put("name","金小洛");
            map.put("club","武大印包");
            map.put("content","一个是华丽短暂的梦，一个事残酷漫长的现实。你会怎么选？");
            map.put("picture1",bitmap2);
            map.put("picture2",bitmap3);
            map.put("picture3",bitmap4);
            listdata.add(map);
            map = new HashMap<>();
        }
            return listdata;
    }

    public Map<String,Object> getTopicMap(){
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.example5);
        Map<String,Object> data = new HashMap<>();
        data.put("image",bitmap1);
        data.put("title","最美丽的风景");
            return data;
    }

    public List<Bitmap> getViewLists(){
        List<Bitmap> viewLists = new ArrayList<Bitmap>();

        viewLists.add(BitmapFactory. decodeResource (getResources(),R.mipmap.example1));
        viewLists.add(BitmapFactory. decodeResource (getResources(),R.mipmap.example2));
        viewLists.add(BitmapFactory. decodeResource (getResources(),R.mipmap.example3));
        viewLists.add(BitmapFactory. decodeResource (getResources(),R.mipmap.example4));
        return viewLists;
    }



    public LinkedList<View> ViewPagerCycle(Context context,List<Bitmap> list)
    {
        LinkedList<View> mViews=null;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if (list != null) {
            //无论是否多于1个，都要初始化第一个（index:0）
            mViews = new LinkedList<>();
            View viewpager_first = mInflater.inflate(R.layout.homepage_viewpager_item,null);
            Bitmap bitmap = list.get(list.size() - 1);
            ((ImageView)viewpager_first.findViewById(R.id.activity_item)).setImageBitmap(bitmap);
            mViews.add(viewpager_first);
            //注意，如果不只1个，mViews比mList多两个（头尾各多一个）
            //假设：mList为mList[0~N-1], mViews为mViews[0~N+1]
            // mViews[0]放mList[N-1], mViews[i]放mList[i-1], mViews[N+1]放mList[0]
            // mViews[1~N]用于循环；首位之前的mViews[0]和末尾之后的mViews[N+1]用于跳转
            // 首位之前的mViews[0]，跳转到末尾（N）；末位之后的mViews[N+1]，跳转到首位（1）
            if( list.size() > 1) { //多于1个要循环
                for (Bitmap d : list) { //中间的N个（index:1~N）
                    View viewpager_item_middle = mInflater.inflate(R.layout.homepage_viewpager_item,null);
                    ((ImageView)viewpager_item_middle.findViewById(R.id.activity_item)).setImageBitmap(d);
                    mViews.add(viewpager_item_middle);
                }
                //最后一个（index:N+1）
                View   viewpager_end = mInflater.inflate(R.layout.homepage_viewpager_item,null);
                bitmap = list.get(0);
                ((ImageView)viewpager_end.findViewById(R.id.activity_item)).setImageBitmap(bitmap);
                mViews.add(viewpager_end);
            }
        }

        return mViews;
    }


    @Override
    protected void onDestroy() {
        if(!scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
        }
        super.onDestroy();
    }
}
