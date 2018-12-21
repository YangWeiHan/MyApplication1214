package com.example.yangweihan1218.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yangweihan1218.Adapter.ShowAdapter;
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

public class ShowActivity extends AppCompatActivity implements IView,View.OnClickListener {
    private ShowAdapter mShowAdapter;
    private CheckBox mCheckBox;
    private List<UserBean.DataBean> dataBeans = new ArrayList<>();
    private IPresenterlmpl mIPresenterlmpl;
    private TextView jieSuan,zongJia;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        //实例 P层的实现类
        mIPresenterlmpl = new IPresenterlmpl(this);
        //获取视图
        initView();
        getData();
    }

    private void initView() {
        mCheckBox =  findViewById(R.id.iv_cricle);
        jieSuan = findViewById(R.id.all_price);
        zongJia = findViewById(R.id.sum_price_txt);
        mRecyclerView = findViewById(R.id.recyclerview);
        mCheckBox.setOnClickListener(this);
        //LinearLayout 布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //实例适配器
        mShowAdapter = new ShowAdapter(this);
        //设置适配器
        mRecyclerView.setAdapter(mShowAdapter);
    //得到商家ADAPter里的点击事件  通过回电值传过来
        mShowAdapter.setmShopCallBackListener(new ShowAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<UserBean.DataBean> list) {
                //在这里 重新遍历已经改变状太后的数据
                // 这里不能break跳出，因为还需要计算后面点击商品的价格和数目，所以必须跑完整个循环
                double totalPrice = 0;
                //设置勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品的总数 和上面的对比  如果相等的话  说明全选了
                int totalNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    //获取商家的商品
                    List<UserBean.DataBean.ListBean> shangpinBean = list.get(i).getList();
                    for (int j = 0; j < shangpinBean.size(); j++) {
                        totalNum = totalNum + shangpinBean.get(j).getNum();
                        //获取选中状态
                        if (shangpinBean.get(j).isCheck()){
                            //总价
                            totalPrice = totalPrice+(shangpinBean.get(j).getPrice()*shangpinBean.get(j).getNum());
                            //总量
                            num = num + shangpinBean.get(j).getNum();
                        }
                    }
                }
                //  如果 num  ！= tatalNum  s
                if (num < totalNum){
                    //不是全选状态
                    mCheckBox.setChecked(false);
                }else {
                    //全选中
                    mCheckBox.setChecked(true);
                }
                //拿到值  进行赋值  合计 计算
                jieSuan.setText("合计："+totalPrice);
                zongJia.setText("去结算（"+num+")");
            }
        });
    }

    private void getData() {
        //得到Map集合
        //存放 两个参数  UID  和   71
        Map<String,String> map = new HashMap<>();
        map.put(Constants.MAP_KEY_GET_PRODUCT_UID,"71");
        mIPresenterlmpl.startRequest(Apis.shopCarUrl,map,UserBean.class);
    }
    //解绑 防止内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPresenterlmpl.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击下面的  全选框   所有的单选框都跟着他的选中状态进行改变
            case R.id.iv_cricle:
                //得到选中状态
                checkSeller(mCheckBox.isChecked());
                //刷新适配器
                mShowAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 修改选中状态，获取价格和数量  需要进行4次循环
     */
    public void checkSeller(boolean CheckAllOrRemoveAll){
            double totalPrice = 0;
            int num = 0;
        for (int i = 0; i < dataBeans.size(); i++) {
             //遍历商家，改变状态
            UserBean.DataBean dataBean = dataBeans.get(i);
            //得到所有商家的选中状态
            dataBean.setCheck(CheckAllOrRemoveAll);
            //遍历所有商品
            List<UserBean.DataBean.ListBean> beanList = dataBeans.get(i).getList();
            for (int j = 0; j <beanList.size() ; j++) {
                //遍历商品，改变状态
               beanList.get(j).setCheck(CheckAllOrRemoveAll);
               //计算总价
                totalPrice = totalPrice + (beanList.get(j).getPrice()*beanList.get(j).getNum());
                //计算 数量
                num = num+beanList.get(j).getNum();
            }
        }
        //如果全部选择  计算
        if (CheckAllOrRemoveAll){
            jieSuan.setText("合计："+totalPrice);
            zongJia.setText("去结算("+num+")");
        }else {
            //如果没有选中
            jieSuan.setText("合计：没钱");
            zongJia.setText("结个屁 ，啥也没买");
        }
    }
    @Override
    public void showResponseData(Object data) {
        if (data instanceof UserBean){
            //得到 数据
            UserBean userBean = (UserBean) data;
            //存到单前页面的集合
            dataBeans = userBean.getData();
            if (dataBeans != null){
                //因为第一个集合是空的所以要remove 这个空集合
                dataBeans.remove(0);
                //给适配器赋值
                mShowAdapter.setMlist(dataBeans);
            }
        }
    }

    @Override
    public void showResponseFail(Object data) {

    }
}
