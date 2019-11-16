package com.example.hp.animation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class SongInformation extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private FragmentManager fragmentManager;
    private MyAdapter myAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_information);



        fragmentManager=getSupportFragmentManager();
        viewPager=findViewById(R.id.view_pager);//找到viewPage
        List<Fragment> fragments=new ArrayList<>();//初始化Fragment列表
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());//
        myAdapter = new MyAdapter(fragmentManager,fragments);//添加三个Fragment
        viewPager.setAdapter(myAdapter);//设置Adapter
        viewPager.addOnPageChangeListener(this);//设置监听器
        viewPager.setCurrentItem(1);


    }

    @Override
    public void onBackPressed() {

        setResult(RESULT_OK,(new Intent()));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
