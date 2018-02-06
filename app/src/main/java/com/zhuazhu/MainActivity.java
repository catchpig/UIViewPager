package com.zhuazhu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import zhuazhu.widget.UIViewPager;

public class MainActivity extends AppCompatActivity {
    private UIViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add("http://cdn.mejust" +
                    ".com/uploadFiles/wisdomprom/promPhoto/20180205/a8bfc926-eac8-45fd-a19c-4fb7c4d788de.jpg");
        }
        mViewPager = findViewById(R.id.pager);
        mViewPager.getLayoutParams().height = (int) ((screenWidth()) / 2.5d);
        mViewPager.setImages(list);
        mViewPager.setDelayTime(4000);
        mViewPager.setInfiniteLoop(true);
        mViewPager.setTranslationSpeed(2000);
//        mViewPager.setIndicatorStyle(R.drawable.rectangle_style);
        mViewPager.setImageLoader(new GlideImageLoader());
        mViewPager.start();
    }
    private int screenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.destroy();
    }
}
