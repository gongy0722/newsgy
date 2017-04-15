package com.crc.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.crc.news.newsgy.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Fragmet需要添加xml布局文件
 */

@ContentView(value = R.layout.time_page)
public class TimePageFragment extends Fragment {

    @ViewInject(R.id.listview)  // 需要添加listItem
    private ListView listview;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("crc",this.getClass() + "---->1: onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("crc",this.getClass() + "---->2: onCreate");
    }

    @Nullable
    @Override  // 每个Fragment都有自己的XML配置文件
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("crc",this.getClass() + "---->3: onCreateView");
        return x.view().inject(this,inflater,null);
//        return View.inflate(this.getActivity(), R.layout.home_page,null);
    }

    @Override  // 布局创建完毕,一般此方法用来初始化数据
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("crc",this.getClass() + "---->4: onViewCreated");
        // 给listView添加数据(创建适配器),不能直接添加,listview.addView();
        // 1: 此处应该先发送http请求,如果进入: onSuccess,则说明数据已经获取
        // 2: Gson 把string转化为model /map
        // 3: 根据for循环,对item里面的view控件进行赋值(ImagerView需要单独获取)
        listview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return new Object();
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;
                if(convertView!=null){ // 说明缓存中已存在List Item
                    view =convertView;
                }else{
                    view = View.inflate(TimePageFragment.this.getActivity(),R.layout.time_list_item,null);
                }
                return view;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("crc",this.getClass() + "---->5: onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("crc",this.getClass() + "---->6: onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("crc",this.getClass() + "---->7: onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("crc",this.getClass() + "---->8: onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("crc",this.getClass() + "---->9: onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("crc",this.getClass() + "---->10: onDestroy");
    }
}