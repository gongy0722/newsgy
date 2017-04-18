package com.crc.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;


import com.crc.news.newsgy.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmet需要添加xml布局文件
 */

@ContentView(value = R.layout.home_page)
public class HomePageFragment extends Fragment {

    @ViewInject(R.id.tabHomeTitle)
    private TabLayout tableHomeTitle;
    @ViewInject(R.id.viewHomePager)
    private ViewPager viewHomePager;

    @Nullable
    @Override  // 每个Fragment都有自己的XML配置文件
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("crc",this.getClass() + "---->2: onCreateView");
        View view =x.view().inject(this,inflater,null);
        initData(view);
        return view;
//        return View.inflate(this.getActivity(), R.layout.home_page,null);
    }


    // 进行组件的赋值操作
    private void initData(View view){

//        tableLayout.setAdapter(new HomeTabFragmentPage(getSupportFragmentManager()));

        HomeTabFragmentPage homeTabFragmentPage=new HomeTabFragmentPage(getActivity().getSupportFragmentManager());

        //设置TabLayout的模式 setMode(TabLayout.MODE_FIXED)
        tableHomeTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tableHomeTitle.addTab(tableHomeTitle.newTab().setText(homeTabFragmentPage.getTitle(0)));
        tableHomeTitle.addTab(tableHomeTitle.newTab().setText(homeTabFragmentPage.getTitle(1)));
        tableHomeTitle.addTab(tableHomeTitle.newTab().setText(homeTabFragmentPage.getTitle(2)));
        tableHomeTitle.addTab(tableHomeTitle.newTab().setText(homeTabFragmentPage.getTitle(3)));
        tableHomeTitle.addTab(tableHomeTitle.newTab().setText(homeTabFragmentPage.getTitle(4)));
        tableHomeTitle.addTab(tableHomeTitle.newTab().setText(homeTabFragmentPage.getTitle(5)));
//        tableHomeTitle.addTab(tableHomeTitle.newTab().setText("要闻"));
//        tableHomeTitle.addTab(tableHomeTitle.newTab().setText("直播"));
//        tableHomeTitle.addTab(tableHomeTitle.newTab().setText("V观"));
//        tableHomeTitle.addTab(tableHomeTitle.newTab().setText("联播"));
//        tableHomeTitle.addTab(tableHomeTitle.newTab().setText("国际"));
//        tableHomeTitle.addTab(tableHomeTitle.newTab().setText("体育"));

        //viewpager加载adapter
        viewHomePager.setAdapter(homeTabFragmentPage);
        //TabLayout加载viewpager
        tableHomeTitle.setupWithViewPager(viewHomePager);
    }


    private class HomeTabFragmentPage extends FragmentPagerAdapter {
        private List<Fragment> listFragment=new ArrayList<Fragment>();                         //fragment列表
        private List<String> listTitle=new ArrayList<String>();                              //tab名的列表

        public HomeTabFragmentPage(FragmentManager fm) {
            super(fm);
            listFragment.add(new HomeYwPageFragment());
            listFragment.add(new HomeZbPageFragment());
            listFragment.add(new HomeVgPageFragment());
            listFragment.add(new HomeLbPageFragment());
            listFragment.add(new HomeGjPageFragment());
            listFragment.add(new HomeTyPageFragment());

            listTitle.add("要闻");
            listTitle.add("直播");
            listTitle.add("V观");
            listTitle.add("联播");
            listTitle.add("国际");
            listTitle.add("体育");
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        public String getTitle(int position){ return listTitle.get(position);}

        @Override
        public int getCount() {
            return listFragment.size();
        }
        //此方法用来显示tab上的名字

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position);
        }
    }


}