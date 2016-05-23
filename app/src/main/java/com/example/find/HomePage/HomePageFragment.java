package com.example.find.HomePage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.find.R;

/**
 * Created by hanjun on 2016/5/23.
 */
public class HomePageFragment extends Fragment {

    private final String TAG = "HomePageActivity";
    RadioButton rb_introduce;
    RadioButton rb_club;
    RadioGroup  rg_group;
    HomePageIntroduceFragment introduce;
    HomePageClubFragment club;
    TextView chat;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.activity_home_page,container,false);
        Log.d("HomePage","onCreate()");
        rb_introduce = (RadioButton)root.findViewById(R.id.rb_introduce);
        rb_club = (RadioButton)root.findViewById(R.id.rb_club);
        rg_group = (RadioGroup)root.findViewById(R.id.rg_group);
        chat = (TextView)root.findViewById(R.id.chat);

        introduce = new HomePageIntroduceFragment();
        club = new HomePageClubFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.fl_content,introduce,"introduce")
                .add(R.id.fl_content, club,"club")
                .commit();
        GroupClickListener clickListener = new GroupClickListener();
        rg_group.setOnCheckedChangeListener(clickListener);

        ClickListener tv_clickListener =new ClickListener();
        chat.setOnClickListener(tv_clickListener);
        return root;
    }

    private class ClickListener implements View.OnClickListener {
        public void onClick(View v) {
            if (v.getId() == R.id.chat) {
                Intent intent = new Intent(v.getContext(), ChatGroupActivity.class);
                startActivity(intent);
            }
        }
    }

    private class GroupClickListener implements RadioGroup.OnCheckedChangeListener{

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Fragment introduce= getFragmentManager().findFragmentByTag("introduce");
            Fragment club= getFragmentManager().findFragmentByTag("club");
            // TODO Auto-generated method stub
            if(checkedId==rb_introduce.getId()){
                getFragmentManager().beginTransaction().hide(club).show(introduce).commit();
            }else if(checkedId==rb_club.getId()){
                getFragmentManager().beginTransaction().hide(introduce).show(club).commit();
            }
        }
    }
}