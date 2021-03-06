package com.example.find.PersonSetting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.find.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.GridViewAdapter;
import InterfaceRealization.ChangeHeadGridViewOperator;

public class ChangeHeadActivity extends AppCompatActivity {

    GridView gridview;
    ArrayList<Map<String, Object>> ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_head);
        gridview = (GridView)findViewById(R.id.gridView);
        ls = getLs();
        GridViewAdapter gridviewAdapter = new GridViewAdapter(this,ls,new ChangeHeadGridViewOperator());
        gridview.setAdapter(gridviewAdapter);
    }

    public ArrayList<Map<String, Object>> getLs(){
        ArrayList<Map<String, Object>> ls = new ArrayList();
        Map map = new HashMap();
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.example5);

        for(int i=0;i<10;i++){
            map.put("content",bitmap2);
            ls.add(map);
            map = new HashMap();
        }
        return ls;
    }
}
