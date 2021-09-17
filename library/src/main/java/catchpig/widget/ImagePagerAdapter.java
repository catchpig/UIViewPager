package catchpig.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

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
    /**
     * 是否无限循环
     */
    private boolean mInfiniteLoop = false;
    private int mCount;
    private OnItemClickListener mListener;
    private ImageLoader mImageLoader;

    public ImagePagerAdapter() {

    }

    public ImagePagerAdapter(List<String> images) {
        if (images != null) {
            mImages = images;
        }
    }

    public void setInfiniteLoop(boolean infiniteLoop) {
        mInfiniteLoop = infiniteLoop;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setImages(List<String> images) {
        mCount = 0;
        if (images != null) {
            mImages = images;
            mCount = mImages.size();
        }
    }

    @Override
    public int getCount() {
        if (mInfiniteLoop) {
            return Integer.MAX_VALUE;
        } else {
            return mCount;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView v = new ImageView(container.getContext());
        final int index = position % mCount;
        if (mImageLoader != null) {
            mImageLoader.displayImage(v, mImages.get(index));
        }
        if (mListener != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(index);
                }
            });
        }
        container.addView(v);
        return v;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView v = (ImageView) object;
        container.removeView(v);
    }

    /**
     * 图片加载处理
     */
    public interface ImageLoader {
        void displayImage(ImageView imageView, String path);
    }

    /**
     * 图片点击监听事件
     */
    public interface OnItemClickListener {
        void onItemClick(int index);
    }
}
