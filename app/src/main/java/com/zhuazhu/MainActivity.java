package com.zhuazhu;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import catchpig.widget.UIViewPager;

public class MainActivity extends AppCompatActivity {
    private UIViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = new ArrayList<>();
        String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.redocn.com%2Fphoto%2F20140313%2FRedocn_2014031216224618.jpg&refer=http%3A%2F%2Fimg.redocn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1634462173&t=a1505f96a898d7ca42b3d1cf9304082c";
        for (int i = 0; i < 5; i++) {
            list.add(url);
        }
        mViewPager = findViewById(R.id.pager);
        mViewPager.getLayoutParams().height = (int) ((screenWidth()) / 2.5d);
        mViewPager.setImages(list);
        mViewPager.setDelayTime(4);
        mViewPager.setInfiniteLoop(true);
        mViewPager.setTranslationSpeed(2000);
//        mViewPager.setIndicatorStyle(R.drawable.rectangle_style);
        mViewPager.setImageLoader(new GlideImageLoader());
        mViewPager.start();
    }

    private int screenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
