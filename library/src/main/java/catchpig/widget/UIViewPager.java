package catchpig.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import catchpig.widget.ImagePagerAdapter.ImageLoader;
import catchpig.widget.ImagePagerAdapter.OnItemClickListener;

/**
 * 创建时间:2018-02-05 17:11<br/>
 * 创建人: 李涛<br/>
 * 修改人: 李涛<br/>
 * 修改时间: 2018-02-05 17:11<br/>
 * 描述:将UIViewPager加入lifecycle,在Activity,Fragment销毁的时候,定时器会自动cancel
 */

public class UIViewPager extends FrameLayout implements ViewPager.OnPageChangeListener, LifecycleObserver {
    private ViewPager mViewPager;
    private ViewPagerScroller mScroller;
    private RadioGroup mRadioGroup;
    private ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor();
    private PagerHandler mHandler;
    private ImagePagerAdapter mImagePagerAdapter;
    private static final int VIEWPAGER_CHANGE = 100;

    public UIViewPager(@NonNull Context context) {
        this(context, null);
    }

    public UIViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(context).inflate(R.layout.widget_uiviewpager, this, true);
        mScroller = new ViewPagerScroller(context);
        mViewPager = v.findViewById(R.id.viewPager);
        mRadioGroup = v.findViewById(R.id.group);
        mHandler = new PagerHandler(this);
        mViewPager.addOnPageChangeListener(this);
        mImagePagerAdapter = new ImagePagerAdapter();
        bindLifecycle(context);
    }

    /**
     * 如果控件实在Fragment中,调用此方法,将控件和fragment的lifecycle做生命周期绑定
     * @param fragment
     */
    public void bindLifecycle(Fragment fragment) {
        fragment.getLifecycle().addObserver(this);
    }

    private void bindLifecycle(Context context) {
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(this);
        }
    }

    /**
     * 设置预加载个数(不设置默认为1)
     *
     * @param limit
     */
    public void setOffscreenPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }

    /**
     * 图片自动平移速度,默认1000
     */
    private int mTranslationSpeed = 1000;

    public void setTranslationSpeed(int translationSpeed) {
        mTranslationSpeed = translationSpeed;
    }

    /**
     * 是否自动播放,默认不自动播放
     */
    private boolean mAutoPlay = false;

    /**
     * 设置是否循环播放(如果设置为true,间隔事件不能为0)
     *
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        mAutoPlay = autoPlay;
    }

    /**
     * 循环时间间隔(秒),默认3秒
     */
    private long mDelayTime = 3;

    /**
     * 设置循环播放时间(循环播放时间大于0,将自动循环播放设置为true)
     *
     * @param delayTime
     */
    public void setDelayTime(@IntRange(from = 1) long delayTime) {
        mDelayTime = delayTime;
        if (delayTime > 0) {
            setAutoPlay(true);
        }
    }

    /**
     * 是否无限循环
     */
    private boolean mInfiniteLoop = false;

    public void setInfiniteLoop(boolean infiniteLoop) {
        mInfiniteLoop = infiniteLoop;
    }

    private int mCount;

    /**
     * 设置图片数据源
     *
     * @param images
     */
    public void setImages(List<String> images) {
        mImagePagerAdapter.setImages(images);
        mCount = images.size();
    }

    /**
     * 设置图片加载处理
     *
     * @param imageLoader
     */
    public void setImageLoader(ImageLoader imageLoader) {
        mImagePagerAdapter.setImageLoader(imageLoader);
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mImagePagerAdapter.setOnItemClickListener(listener);
    }

    /**
     * 启动轮播
     */
    public void start() {
        mImagePagerAdapter.setInfiniteLoop(mInfiniteLoop);
        mViewPager.setAdapter(mImagePagerAdapter);
        if (mCount < 4) {
            mViewPager.setOffscreenPageLimit(mCount);
        } else {
            mViewPager.setOffscreenPageLimit(4);
        }
        initRadioButton();
        startTimer();
    }

    private RunnableScheduledFuture mTimer;

    /**
     * 开启时间定时器轮播
     */
    private void startTimer() {
        //循环播放时间为0,不循环播放
        if (!mAutoPlay) {
            return;
        }
        if (mDelayTime <= 0) {
            return;
        }
        if (mTimer != null && mTimer.isPeriodic()) {
            return;
        }
        mTimer = (RunnableScheduledFuture) mExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(VIEWPAGER_CHANGE);
            }
        }, mDelayTime, mDelayTime, TimeUnit.SECONDS);
    }

    /**
     * 指示灯样式
     */
    private int mIndicatorStyle = R.drawable.carousel_bg;

    /**
     * 设置指示灯样式
     *
     * @param indicatorStyle
     */
    public void setIndicatorStyle(@DrawableRes int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
    }

    /**
     * 初始化指示灯个数
     */
    private void initRadioButton() {
        mRadioGroup.removeAllViews();
        for (int i = 0; i < mCount; i++) {
            ImageView img = new ImageView(getContext());
            img.setPadding(dpToPxInt(5), 0, 0, 0);
            if (i == 0) {
                img.setEnabled(true);
            } else {
                img.setEnabled(false);
            }
            img.setImageResource(mIndicatorStyle);
            mRadioGroup.addView(img, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT);
        }
    }

    /**
     * 设置当前选中指示灯
     *
     * @param position
     */
    private synchronized void setCurrentRadio(int position) {
        for (int i = 0; i < mCount; i++) {
            View v = mRadioGroup.getChildAt(i);
            boolean enabled = v.isEnabled();
            boolean flag = position == i;
            if (flag || enabled) {
                ImageView img = new ImageView(getContext());
                if (flag) {
                    img.setEnabled(true);
                }
                if (enabled) {
                    img.setEnabled(false);
                }
                mRadioGroup.removeViewAt(i);
                img.setImageResource(mIndicatorStyle);
                img.setPadding(dpToPxInt(5), 0, 0, 0);
                mRadioGroup.addView(img, i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

        }
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    private int dpToPxInt(float dp) {
        return (int) ((dp * getResources().getDisplayMetrics().density) + 0.5);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        //销毁定时器
        mExecutorService.shutdown();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int i = position % mCount;
        setCurrentRadio(i);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 轮播下一张图片
     */
    public void playNext() {
        int cuurent = mViewPager.getCurrentItem() + 1;
        if (cuurent > mImagePagerAdapter.getCount()) {
            cuurent = 1;
        }
        mScroller.setScrollDuration(mTranslationSpeed);
        mScroller.initViewPagerScroll(mViewPager);//这个是设置切换过渡时间为2秒
        mViewPager.setCurrentItem(cuurent);
    }

    public static class PagerHandler extends Handler {
        private UIViewPager mPager;

        public PagerHandler(UIViewPager pager) {
            mPager = pager;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == VIEWPAGER_CHANGE && mPager != null) {
                mPager.playNext();
            }
        }
    }
}
