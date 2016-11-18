package com.jeff.shareapp.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.jeff.shareapp.R;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by 张武 on 2016/5/26.
 * 用ImageLoader获取图片
 */
public class MyImageLoader {


    // 异步加载图片
    private  ImageLoader mImageLoader;
    private  DisplayImageOptions options;
    private Context context;

    public MyImageLoader(Context context) {
        this.context = context;
        initImageloader();
    }

    private void initImageloader(){
        // 使用ImageLoader之前初始化
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new LruMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(12 * 1024 * 1024)
                .discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);

        // 获取图片加载实例
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.ic_default_pic)
                .showImageForEmptyUri(R.mipmap.ic_default_pic)
                .showImageOnFail(R.mipmap.ic_default_pic)
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY).build();

    }

    public void loadImage(String picUrl,ImageView mImageView){
        mImageLoader.displayImage(picUrl, mImageView,
                options);
    }

}
