package com.crc.news.newsgy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.crc.news.utils.SharedPreUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_index)
public class IndexActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_index);
        x.view().inject(this);

        SharedPreferences ref = this.getSharedPreferences("config", MODE_PRIVATE);
        // 像config.xml中写入key与value
        SharedPreferences.Editor editor = ref.edit();
        editor.putString("name", "admin");
        editor.commit();  // 真正写入
        Log.i("crc", ref.getString("name", "abc") + ref.getString("password", "admin****"));

    }

//    @Event(value = R.id.abc)
//    private void abc(View view){
//        Log.i("jxy","abc............");
//    }

    @Override
    protected void onResume() {
        super.onResume();
        // 此处可以判断用户是否开启网络, 或者启动Service下载网络数据,
        // 两秒后跳转到引导页面
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                // 默认跳转到欢迎页面,如果欢迎页面已显示,则直接跳转主页面
                if (SharedPreUtils.getBoolean(IndexActivity.this, "welcome_show", false)) {
                    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                    // 标准模式在同一个APP中所有Activity都在同一个栈
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    // 启动主页面
                    startActivity(intent);
                } else {
                    startActivity(new Intent(IndexActivity.this, WelcomeActivity.class));
                }
            }
        }, 2000);
    }
}
