package com.example.wwwqq.day8lx2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;

public class Main_Two extends AppCompatActivity
{
    private ViewPager pager;
    private RadioGroup group;
    private RadioButton r1,r2,r3;
    private ArrayList<Fragment> arrayList=new ArrayList<>();
    private DbManager db;
    private String zhang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_two);
        pager=findViewById(R.id.pager);
        group=findViewById(R.id.group);
        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        r3=findViewById(R.id.r3);

        x.view().inject(this);

        DbManager.DaoConfig daoConfig=new DbManager.DaoConfig();
        daoConfig.setDbName("lalala.db")
                .setDbVersion(1);
        db=x.getDb(daoConfig);

        arrayList.clear();

        arrayList.add(new Top1(this,db));
        arrayList.add(new Top2(this));
        pager.setAdapter(new Adapter(getSupportFragmentManager()));

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i==0)
                {
                    r1.setChecked(true);
                }else if(i==1)
                {
                    r2.setChecked(true);
                }else if(i==2)
                {
                    r3.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(R.id.r1==i)
                {
                    pager.setCurrentItem(0);
                }else if(R.id.r2==i)
                {
                    pager.setCurrentItem(1);
                }else if(R.id.r3==i)
                {
                    pager.setCurrentItem(2);
                }
            }
        });

    }

    class Adapter extends FragmentPagerAdapter
    {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }
    }
}
