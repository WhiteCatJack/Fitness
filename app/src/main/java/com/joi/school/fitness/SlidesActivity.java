package com.joi.school.fitness;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.joi.school.fitness.home.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.Arrays;
import java.util.List;

/**
 * Description.
 *
 * @author Joi
 * createAt 2019/4/14 0014 21:49
 */
public class SlidesActivity extends Activity {

    private Banner mBannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_slides);

        mBannerView = findViewById(R.id.banner);

        Banner banner = mBannerView;
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        //资源文件
        Integer[] images = {
                R.drawable.slides_1,
                R.drawable.slides_2,
                R.drawable.slides_3,
                R.drawable.slides_4,
                R.drawable.slides_5,
                R.drawable.slides_6
        };
        List<Integer> imagesList = Arrays.asList(images);
        banner.setImages(imagesList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(false);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        findViewById(R.id.bt_go_to_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
