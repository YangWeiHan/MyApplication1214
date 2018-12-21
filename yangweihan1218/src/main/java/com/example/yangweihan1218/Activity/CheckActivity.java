package com.example.yangweihan1218.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yangweihan1218.R;

public class CheckActivity extends AppCompatActivity  {
    private Button shop_car,erjiliandong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initView();
    }

    private void initView() {
        shop_car = findViewById(R.id.shop_car);
        erjiliandong = findViewById(R.id.erjiliandong);
        //点击跳转购物侧和
        shop_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop_car.setTextColor(Color.MAGENTA);
                shop_car.setBackgroundResource(R.color.colorPrimaryDark);
                startActivity(new Intent(CheckActivity.this,ShowActivity.class));
            }
        });
        //点击跳转二级联动
        erjiliandong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erjiliandong.setTextColor(Color.MAGENTA);
                erjiliandong.setBackgroundResource(R.color.colorPrimaryDark);
                startActivity(new Intent(CheckActivity.this,LianDongActivity.class));
            }
        });
    }


}
