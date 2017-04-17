package com.crc.news.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crc.news.model.News;
import com.crc.news.newsgy.R;
import com.google.gson.Gson;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmet需要添加xml布局文件
 */

@ContentView(value = R.layout.time_page)
public class TimePageFragment extends Fragment {

    @ViewInject(R.id.listview)  // 需要添加listItem
    private ListView listview;
    private List<News> newsList;

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
        RequestParams params = new RequestParams("http://hiwbs101083.jsp.jspee.com.cn/NewServlet");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();

                // 2: Gson 把string转化为model /map
                Gson gson=new Gson();
                newsList=gson.fromJson(result,new TypeToken<List<News>>(){}.getType());

                // 3: 根据for循环,对item里面的view控件进行赋值(ImagerView需要单独获取)
                listview.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return newsList.size();
                    }

                    @Override
                    public Object getItem(int position) {
//                return new Object();
                        return newsList.get(position);
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
                            TextView time = (TextView)view.findViewById(R.id.newsTime);
                            TextView title=(TextView)view.findViewById(R.id.newsTitle);
                            ImageView image=(ImageView)view.findViewById(R.id.newsImg);

                            News news=newsList.get(position);

                            time.setText(news.getTime());
                            title.setText(news.getTitle());
// 设置加载图片的参数
                            ImageOptions options = new ImageOptions.Builder()
// 是否忽略GIF格式的图片
                                    .setIgnoreGif(false)
// 图片缩放模式
                                    .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
// 下载中显示的图片
                                    .setLoadingDrawableId(R.mipmap.index)
// 下载失败显示的图片
                                    .setFailureDrawableId(R.mipmap.index)
// 得到ImageOptions对象
                                    .build();
// 加载图片
                            x.image().bind(image,news.getImgUrl(), options, new Callback.CommonCallback<Drawable>() {
                                @Override
                                public void onSuccess(Drawable arg0) {
                                    Log.i("crc", "onSuccess........");
                                }

                                @Override
                                public void onFinished() {
                                    Log.i("crc", "onFinished........");
                                }

                                @Override
                                public void onError(Throwable arg0, boolean arg1) {

                                    Log.i("crc", "onError........");
                                }

                                @Override
                                public void onCancelled(Callback.CancelledException arg0) {
                                    Log.i("crc", "onCancelled........");
                                }
                            });
                        }
                        return view;
                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

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