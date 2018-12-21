package com.example.yangweihan1218.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yangweihan1218.Adapter.LeftRecyclerViewAdapter;
import com.example.yangweihan1218.Adapter.RightRecyclerViewAdapter;
import com.example.yangweihan1218.Apis;
import com.example.yangweihan1218.Bean.UserBean;
import com.example.yangweihan1218.Constants;
import com.example.yangweihan1218.Presenter.IPresenterlmpl;
import com.example.yangweihan1218.R;
import com.example.yangweihan1218.View.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 俩及脸东
* */
//二级联动也要IVew
public class LianDongActivity extends AppCompatActivity implements IView {
    //得到 P层的实现类
    private IPresenterlmpl iPresenterlmpl;
    //实现两个适配器
    private LeftRecyclerViewAdapter leftAdapter;
    private RightRecyclerViewAdapter rightAdapter;
    //在主页面有两个recyclerView  一左一右
    private RecyclerView left_recyclerView,right_recyclerView;
    //得到 商家的集合
    private List<UserBean.DataBean> dataBeanList ;
    //设置一个索引
    private int index;
    private Button goto_banner;
    /*
    * 初始化 左侧recyclerView 加载左侧
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lian_dong);
        //实例化P层的实现类
        iPresenterlmpl = new IPresenterlmpl(this);
        goto_banner = findViewById(R.id.goto_banner);
        goto_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LianDongActivity.this,BannerActivity.class));
            }
        });
        initViewLeft();
        initViewRight();
        getShopData();
    }
    //左边商家recycleView的UI
    private void initViewLeft() {
        //获取资源ID
        left_recyclerView = findViewById(R.id.left_recyclerView);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        left_recyclerView.setLayoutManager(layoutManager);
        //设置分割线  这个分割线只能用于线性布局
        left_recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //哪的商家的适配器
        leftAdapter = new LeftRecyclerViewAdapter(this);
        //设置适配器
        left_recyclerView.setAdapter(leftAdapter);
        //得到  商家适配器里的点击监方法
        leftAdapter.setOnClickListener(new LeftRecyclerViewAdapter.onClickListener() {
            @Override//商家的索引和ID
            public void onClick(int position, String cid) {
                //点击以后 当期index  等于 商家的索引  就能得到商家中的商品
                index = position;
                Toast.makeText(LianDongActivity.this,""+position,Toast.LENGTH_LONG).show();
                //拿到接口
                getShopData();
            }
        });
    }
    //右边商家recycleView的UI
    private void initViewRight() {
        //获取资源ID
        right_recyclerView = findViewById(R.id.right_recyclerView);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        right_recyclerView.setLayoutManager(layoutManager);
        //商品的适配器
        rightAdapter = new RightRecyclerViewAdapter(this);
        //设置适配器
        right_recyclerView.setAdapter(rightAdapter);
    }
    //拼接接口  拿到数据
    private void getShopData(){
        Map<String,String> map = new HashMap<>();
        map.put(Constants.MAP_KEY_GET_PRODUCT_UID,"71");
        iPresenterlmpl.startRequest(Apis.shopCarUrl,map,UserBean.class);
    }

    @Override
    public void showResponseData(Object data) {
        if (data instanceof UserBean){
            //得到商家的数据
            UserBean userBean = (UserBean) data;
            //把数据存到当前页面的集合
            dataBeanList = userBean.getData();

           /* if (listBeanList != null){
                listBeanList.remove(0);
                leftAdapter.setMlist(listBeanList);
            }*/
           //设置适配器
           leftAdapter.setMlist(dataBeanList);
           //设置商品的适配器  得到商品的数据
           rightAdapter.setMlist(dataBeanList.get(index).getList());

        }
    }

    @Override
    public void showResponseFail(Object data) {
    }

    //解决内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenterlmpl.onDetach();
    }
}
