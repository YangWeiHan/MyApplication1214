package com.example.yangweihan1218.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yangweihan1218.Bean.UserBean;
import com.example.yangweihan1218.R;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> {
    //上下文
    private Context context;
    //商家的集合
    private List<UserBean.DataBean> mlist;
    //构造
    public ShowAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //获取视图
        View view = View.inflate(context,R.layout.show_seller_car_adapter,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        //设置商家的名字
        myViewHolder.msellerName.setText(mlist.get(i).getSellerName());
        //在这块儿拿到商品适配器里的数据  放在商家的适配器里面 给到商品的集合 之后这个商家里就有数据了
        final ProductsAdapter productsAdapter = new ProductsAdapter(context,mlist.get(i).getList());
        //线性布局的管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        //设置方向
        myViewHolder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //拿到商品适配器里的数据
        myViewHolder.mRecyclerView.setAdapter(productsAdapter);
        //获取 单选框 根据下标获取    并且得到选中状态
        myViewHolder.checkBox.setChecked(mlist.get(i).isCheck());
        //商家的适配器  拿到从商品适配器里得到的东西以后  进行操作
        productsAdapter.setListener(new ProductsAdapter.ShopCallBackListener() {
            //进行操作的步骤在这里
            @Override
            public void callBack() {
                 if (mShopCallBackListener != null){
                     mShopCallBackListener.callBack(mlist);
                 }
                    //得到商品的集合
                 List<UserBean.DataBean.ListBean> listBeanList = mlist.get(i).getList();
                    //创建一个临时的标志位，用来记录现在点击的状态
                boolean isAllChecked = true;
                //循环商品  得到所有单选框的状态
                for (UserBean.DataBean.ListBean listBean : listBeanList){
                 if (!listBean.isCheck()){
                     //只要有一个商品未选中，标志位设置成false，并且跳出循环
                     isAllChecked = false;
                     break;
                    }
                }
                //刷新商家的状态
                myViewHolder.checkBox.setChecked(isAllChecked);
                mlist.get(i).setCheck(isAllChecked);
            }
        });

        //监听 CheckBox的 点击监听事件
        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先改变自己的标示位
                mlist.get(i).setCheck(myViewHolder.checkBox.isChecked());
                //调用产品adapter 的方法  用来做全选和全不选
                productsAdapter.selectOrRemoveAll(myViewHolder.checkBox.isChecked());
            }
        });
    }

    //得到集合的长度\大小
    @Override
    public int getItemCount() {
        return mlist.size();
    }
    //set方法
    public void setMlist(List<UserBean.DataBean> mlist) {
        this.mlist = mlist;
        //刷新
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView mRecyclerView;
        TextView msellerName;//商家的名字
        CheckBox checkBox; //设置布局里的单选框

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //获取资源ID
            msellerName = itemView.findViewById(R.id.tv_shop);
            checkBox = itemView.findViewById(R.id.check_shop);
            mRecyclerView = itemView.findViewById(R.id.recycler_shop);
        }
    }
    //设置接口回调  让主页面那个商家的点击事件
    private ShopCallBackListener mShopCallBackListener;

    public void setmShopCallBackListener(ShopCallBackListener mShopCallBackListener) {
        this.mShopCallBackListener = mShopCallBackListener;
    }

    public interface ShopCallBackListener{
        void callBack(List<UserBean.DataBean> list);
    }
}
