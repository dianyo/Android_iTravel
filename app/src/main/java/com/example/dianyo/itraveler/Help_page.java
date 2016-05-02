package com.example.dianyo.itraveler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class Help_page extends AppCompatActivity {

    private ViewPager myViewPager;
    private View v1,v2,v3,v4;
    private ArrayList<View> viewList = new ArrayList<View>();
    private RadioGroup myRadio;
    private RadioButton r1, r2, r3, r4;
    private Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        processViews();
        processControllers();

    }


    private void processViews(){
        myViewPager = (ViewPager) findViewById(R.id.viewpager);
        myRadio = (RadioGroup) findViewById(R.id.radioGroup);
        r1 = (RadioButton) findViewById(R.id.firstPage);
        r2 = (RadioButton) findViewById(R.id.secondPage);
        r3 = (RadioButton) findViewById(R.id.thirdPage);
        r4 = (RadioButton) findViewById(R.id.fourthPage);
        skip = (Button) findViewById(R.id.skip);
    }

    private void processControllers(){

        //set fragments
        final LayoutInflater myInflater = getLayoutInflater().from(this);
        v1 = myInflater.inflate(R.layout.first_page, null);
        v2 = myInflater.inflate(R.layout.second_page, null);
        v3 = myInflater.inflate(R.layout.third_page, null);
        v4 = myInflater.inflate(R.layout.fourth_page, null);

        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
        viewList.add(v4);

        myViewPager.setAdapter(new myViewPagerAdapter(viewList));
        myViewPager.setCurrentItem(0);
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        myRadio.check(R.id.firstPage);
                        break;
                    case 1:
                        myRadio.check(R.id.secondPage);
                        break;
                    case 2:
                        myRadio.check(R.id.thirdPage);
                        break;
                    case 3:
                        myRadio.check(R.id.fourthPage);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int stat) {
            }
        });

        //set skip
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set click the radio
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(0);
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(1);
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(2);
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(3);
            }
        });

    }
    public void beginFromHelp(View view){
        finish();
    }
}
