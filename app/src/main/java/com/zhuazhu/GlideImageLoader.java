package com.zhuazhu;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import catchpig.widget.ImagePagerAdapter;

/**
 * 创建时间:2018-02-06 12:56<br/>
 * 创建人: 李涛<br/>
 * 修改人: 李涛<br/>
 * 修改时间: 2018-02-06 12:56<br/>
 * 描述:
 */

public class GlideImageLoader implements ImagePagerAdapter.ImageLoader {
    @Override
    public void displayImage(ImageView imageView, String path) {
        Glide.with(imageView.getContext()).load(path).into(imageView);
    }
}
