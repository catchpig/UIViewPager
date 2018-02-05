package zhuazhu.widget;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2018/2/5 21:53<br/>
 * 创建人: 李涛<br/>
 * 修改人: 李涛<br/>
 * 修改时间: 2018/2/5 21:53<br/>
 * 描述:
 */

public class ImagePagerAdapter extends PagerAdapter {
    public List<String> mImages = new ArrayList<>();
    private boolean mAutoPlay = false;
    public ImagePagerAdapter(){

    }
    public ImagePagerAdapter(List<String> images){
        if(images!=null){
            mImages = images;
        }
    }
    public void setImages(List<String> images){
        if(images!=null){
            mImages = images;
        }
    }
    @Override
    public int getCount() {
        if(mAutoPlay){
            return Integer.MAX_VALUE;
        }else{
            return mImages.size();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
