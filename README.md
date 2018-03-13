# UIViewPager
[![Release](https://jitpack.io/v/zhuazhu/UIViewPager.svg)](https://jitpack.io/#zhuazhu/UIViewPager)

![image](https://github.com/zhuazhu/UIViewPager/blob/master/images/7466622861800032062.png)

## 1.用法

使用前，对于Android Studio的用户，可以选择添加:
在Project的build.gradle中添加:
```
   allprojects {
    	repositories {
    		maven { url 'https://jitpack.io' }
    	}
    }
   ```
添加依赖:
```
    implementation 'com.github.zhuazhu:UIViewPager:last_version'
```
## 2.方法

|方法|描述|
|:--|:--|
|setTranslationSpeed(int translationSpeed)|图片自动平移速度,默认1000|
|setAutoPlay(boolean autoPlay)|设置是否循环播放(如果设置为true,间隔事件不能为0)|
|setDelayTime(long delayTime)|设置循环播放时间(循环播放时间大于0,将自动循环播放设置为true)|
|setInfiniteLoop(boolean infiniteLoop)|是否无限循环|
|setImages(List<String> images)|设置图片数据源|
|setImageLoader(ImageLoader imageLoader)|设置图片加载处理|
|setOnItemClickListener(OnItemClickListener listener)|设置图片点击事件|
|setIndicatorStyle(int indicatorStyle)|设置指示灯样式|
|start()|启动轮播|
|destroy()|销毁定时器(必须在activity,fragment,dialog销毁时调用此方法)|


## 3.代码参考
1.实现ImageLoader接口,选择需要加载图片的第三方库(Glide,Picasso...)如下代码使用的是Glide
```
    public class GlideImageLoader implements ImagePagerAdapter.ImageLoader {
        @Override
        public void displayImage(ImageView imageView, String path) {
            Glide.with(imageView.getContext()).load(path).into(imageView);
        }
    }
```
2.方法的实现
```
    private UIViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add("http://cdn.mejust.com/uploadFiles/wisdomprom/"+
            "promPhoto/20180205/a8bfc926-eac8-45fd-a19c-4fb7c4d788de.jpg");
        }
        mViewPager = findViewById(R.id.pager);
        mViewPager.getLayoutParams().height = (int) ((screenWidth()) / 2.5d);
        mViewPager.setImages(list);// 设置数据源
        mViewPager.setDelayTime(4000);//设置轮播间隔时间
        mViewPager.setInfiniteLoop(true);//设置是否无线循环
        mViewPager.setTranslationSpeed(2000);//设置平移过度时间
        mViewPager.setImageLoader(mViewPager.setImageLoader(new GlideImageLoader()););
        mViewPager.start();
    }
 ```
3.设置指示灯样式代码如下:

------圆形图标:
```
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:state_enabled="true">
            <shape
                android:shape="oval">
                <size android:width="7dp" android:height="7dp"/>
                <solid android:color="@color/c_fff"/>
                <stroke android:width="0.5dp" android:color="@color/c_14000000"/>
            </shape>
        </item>
        <item android:state_enabled="false">
            <shape
                android:shape="oval">
                <size android:width="7dp" android:height="7dp"/>
                <solid android:color="@color/c_4d000000"/>
            </shape>
        </item>
    </selector>
```

------长方形图标:

```
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:state_enabled="true">
            <shape android:shape="rectangle">
                <size android:height="4dp" android:width="20dp"/>
                <solid android:color="@color/c_fff"/>
                <corners android:radius="1dp"/>
            </shape>
        </item>
        <item android:state_enabled="false">
            <shape android:shape="rectangle">
                <size android:height="4dp" android:width="10dp"/>
                <solid android:color="@color/c_000"/>
                <corners android:radius="1dp"/>
            </shape>
        </item>
    </selector>
```
4.在在activity,fragment,dialog销毁时调用destroy()方法,Activity中代码如下
```
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.destroy();
    }
```