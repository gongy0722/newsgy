package com.crc.news.newsgy;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.crc.news.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {

    private ViewPager viewpager;
    private LinearLayout ll;
    private int pointMoveWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // 绑定组件
        initView();
        // 初始化数据
        initData();
        Log.i("crc",this.getResources().getDisplayMetrics().density + "");  // 3.0
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取红点移动的距离(两个灰点离左边的距离)
        //Log.i("crc","0point:" + ll.getChildAt(0).getLeft() + ",1point:" + ll.getChildAt(1).getLeft());
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        ll = (LinearLayout) findViewById(R.id.ll);
    }

    private void initData() {
        // 给ViewPager适配Item一般都ImageView
        viewpager.setAdapter(new WelcomePage());
        // 动态给线性 布局添加三个小灰点
        for (int i = 0; i < 3; i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.welcome_point_gray);
            // 代码中所有的数字的单位都是像素 px
            int px = DensityUtils.dpi2px(this,10);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(px,px);
            if(i>0){
                param.leftMargin = 20; // 20px
            }
            view.setLayoutParams(param);
            ll.addView(view);
        }


        // 通过对Tree观察者监听,可以动态计算移动的距离
        ll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("jxy", "onGlobalLayout........");
                Log.i("jxy", "0point:" + ll.getChildAt(0).getLeft() + ",1point:" + ll.getChildAt(1).getLeft());
                pointMoveWidth = ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
                ll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        // 注册一个监听页面切换事件
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             *
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("crc","当前页面的索引:" + position + ",移动距离百分比:" + positionOffset + ",移动的像素:" + positionOffsetPixels);
                // 获取red_poinrt的组件,并且设置参数
                View redPoint = findViewById(R.id.red_point);
                RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)redPoint.getLayoutParams();
                param.leftMargin = (int)(pointMoveWidth * positionOffset) + position * pointMoveWidth;
                redPoint.setLayoutParams(param);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("crc","当前被选择的页面:" + position);
 //               View redPoint = findViewById(R.id.red_point);
 //               RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)redPoint.getLayoutParams();
 //               param.leftMargin = position * pointMoveWidth;
 //               redPoint.setLayoutParams(param);
            }

            /**
             *
             * @param state :页面的状态: 0 代表未移动   1: 正在移动   2： 正在切换
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("crc","state:" + state);
            }
        });
    }

    private class WelcomePage extends PagerAdapter {

        private int[] ids = null;
        private List<ImageView> iList = null;

        public WelcomePage(){
            ids = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
            iList = new ArrayList<ImageView>();
            // 创建一个ListView<ImageView>来存储图片,
            for(int i=0;i<ids.length;i++){
                ImageView imageView = new ImageView(WelcomeActivity.this);
                // 给当前ImageView设置背景图
                imageView.setBackgroundResource(ids[i]);
                iList.add(imageView);
            }
        }

        @Override  // 返回集合大小
        public int getCount() {
            return iList.size();
        }

        @Override // 实例化每一个Item,其实就是View
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view =  (ImageView)iList.get(position);
            Log.i("crc","当前的ViewPager对象:" + container + ",position:" + position + ",ImageView:" + view);
            // 返回之前必须把当前View对象添加到容器中
            container.addView(view); // lv.addView(view); 只能使用适配器
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("crc","当前销毁的对象:" + object);
            container.removeView((ImageView)object);
        }

        @Override  //
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
