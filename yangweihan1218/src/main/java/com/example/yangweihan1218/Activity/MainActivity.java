package com.example.yangweihan1218.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yangweihan1218.Dao.Dao;
import com.example.yangweihan1218.R;
import com.example.yangweihan1218.ZDYView.WeekFlowLayout;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Dao dao;
    private ImageView imageView,qq_image;
    private TextView qq_name;
    private Button qq_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化一个Dao层
        dao = new Dao(this);

        init();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CheckActivity.class));
            }
        });

        qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得UMShareAPI实例
                UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                //开始登录
                //第一个参数：上下文
                //第二个参数，登录哪种平台
                //第三个参数，添加回调
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    /**
                     * 开始登录回调
                     * @param share_media
                     */
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }
                    /**
                     * 登录完成
                     * @param share_media
                     * @param i
                     * @param map
                     */
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        //头像，昵称，
                        //获取名字
                        String screen_name = map.get("screen_name");
                        //获取头像
                        String image_url = map.get("profile_image_url");
                        Glide.with(MainActivity.this).load(image_url).into(qq_image);
                        qq_name.setText(screen_name);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    private void init() {
        //获取自定义布局的资源ID
        qq_image = findViewById(R.id.qq_image);
        qq_login = findViewById(R.id.qq_login);
        qq_name = findViewById(R.id.qq_name);
        imageView = findViewById(R.id.jiantou);
        final WeekFlowLayout flowLayout = findViewById(R.id.flowLayout);
        //点击 清除所有
        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除自定义View  里的所有视图
                flowLayout.removeAllViews();
            }
        });

        //输入框的资源ID
        final EditText main_edit = findViewById(R.id.main_edit);
        //获取按钮的资源ID  添加点击监听事件
        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //获取随机字符串   ，当做唯一标示
            UUID uuid = UUID.randomUUID();
            //实例一个TextView 用来存数据
            final TextView textView = new TextView(MainActivity.this);
            //获取输入框里的值
            textView.setText(main_edit.getText());
            //获取出来以后  拿到这个值
            String s = main_edit.getText().toString();
            dao.add(s);
            //设置字体颜色
            textView.setTextColor(Color.BLACK);
            //设置背景颜色
            textView.setBackgroundResource(R.color.colorPrimary);
            //添加View
            flowLayout.addView(textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //给每一个自定义View  设置一个 唯一的标示符
                    String uuid = String.valueOf(v.getTag());
                    Toast.makeText(MainActivity.this,"字符串"+textView.getText().toString()+uuid,Toast.LENGTH_SHORT).show();
                }
            });
            }
        });
    }

}
