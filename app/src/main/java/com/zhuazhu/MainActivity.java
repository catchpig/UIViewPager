package com.zhuazhu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zhuazhu.widget.UIViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIViewPager pager = findViewById(R.id.pager);
        pager.setDelayTime(4000);

    }
}
