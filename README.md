# UIViewPager
[![Release](https://jitpack.io/v/zhuazhu/UIViewPager.svg)](https://jitpack.io/#zhuazhu/UIViewPager)

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
```java
	compile 'com.github.zhuazhu:UIViewPager:last_version'
```
## 2.方法

|方法|描述|
|:--:|:--:|
|setTranslationSpeed(int translationSpeed)|图片自动平移速度,默认1000|
|setAutoPlay(boolean autoPlay)|设置是否循环播放(如果设置为true,间隔事件不能为0)|
|setDelayTime(long delayTime)|设置循环播放时间(循环播放时间大于0,将自动循环播放设置为true)|
|setInfiniteLoop(boolean infiniteLoop)|是否无限循环|
|setImages(List<String> images)|设置图片数据源|
|setImageLoader(ImageLoader imageLoader)|设置图片加载处理|
|setOnItemClickListener(OnItemClickListener listener)|设置图片点击事件|
|start()|启动轮播|
|destroy()|销毁定时器(必须在activity,fragment,dialog销毁时调用此方法)|
|||