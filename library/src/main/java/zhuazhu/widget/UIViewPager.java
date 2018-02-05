package zhuazhu.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建时间:2018-02-05 17:11<br/>
 * 创建人: 李涛<br/>
 * 修改人: 李涛<br/>
 * 修改时间: 2018-02-05 17:11<br/>
 * 描述:
 */

public class UIViewPager extends FrameLayout implements OnPageChangeListener{
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private Timer mTimer;
    private TimerTask mTask;
    private PagerHandler mHandler;
    private static final int VIEWPAGER_CHANGE = 100;
    public UIViewPager(@NonNull Context context) {
        this(context,null);
    }

    public UIViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UIViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(context).inflate(R.layout.widget_uiviewpager, this, true);
        mViewPager = v.findViewById(R.id.viewPager);
        mRadioGroup = v.findViewById(R.id.group);
        mHandler = new PagerHandler();
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 是否自动播放,默认不自动播放
     */
    private boolean mAutoPlay = false;
    public void setAutoPlay(boolean autoPlay){
        mAutoPlay = autoPlay;
    }
    /**
     * 循环时间间隔(毫秒)
     */
    private long mDelayTime;
    public void setDelayTime(long delayTime){
        setAutoPlay(true);
        mDelayTime = delayTime;
    }
    private int mCount;
    public void setCount(int count){
        mCount = count;
    }
    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(PagerAdapter adapter){
        mViewPager.setAdapter(adapter);
        initRadioButton();
        startTimer();
    }
    /**
     * 开启时间定时器轮播
     */
    private void startTimer() {
        //循环播放时间为0,不循环播放
        if(mDelayTime <=0){
            return;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(VIEWPAGER_CHANGE);
            }
        };
        mTimer.schedule(mTask, mDelayTime, mDelayTime);
    }

    /**
     * c
     */
    private void initRadioButton() {
        mRadioGroup.removeAllViews();
        for (int i = 0; i < mCount; i++) {
            ImageView img = new ImageView(getContext());
            img.setImageResource(R.drawable.carousel_bg);
            img.setPadding(dpToPxInt(5), 0, 0, 0);
            img.setEnabled(false);
            mRadioGroup.addView(img, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT);
        }
        mRadioGroup.getChildAt(0).setEnabled(true);
    }
    /**
     * 设置当前选中圆点
     *
     * @param postion
     */
    private void setCurrentRadio(int postion) {
        for (int i = 0; i < mCount; i++) {
            mRadioGroup.getChildAt(i).setEnabled(false);
        }
        mRadioGroup.getChildAt(postion).setEnabled(true);
    }
    private int dpToPxInt(float dp){
        return (int) ((dp * getResources().getDisplayMetrics().density)+0.5);
    }

    /**
     * 销毁定时器
     */
    public void destroy(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int i = position% mCount;
        setCurrentRadio(i);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public static class PagerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == VIEWPAGER_CHANGE){

            }
        }
    }
}
