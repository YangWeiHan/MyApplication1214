package com.example.yangweihan1218.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yangweihan1218.Bean.UserBean;
import com.example.yangweihan1218.R;
import com.example.yangweihan1218.ZDYView.CustomCounterView;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context context;
    //这里是 商品的集合
    private List<UserBean.DataBean.ListBean> listBeans;
    //把商品集合当做是参数  所以 lisrBeans 就不用实例的   直接等于参数里的集合就可以了
    public ProductsAdapter(Context context, List<UserBean.DataBean.ListBean> list) {
        this.context = context;
        listBeans = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //获取视图
        View view = View.inflate(context,R.layout.products_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        //截取字符串    截图这个图片 根据字符串截取成一个数组
        //  我们只要第一张  所以split("\\|")[0]
        String url = listBeans.get(i)
                              .getImages()
                              .split("\\|")[0]
                                //http 替代https
                              .replace("https","http");
        //用Glide 获取 图片
        Glide.with(context).load(url).into(myViewHolder.image_product);
        //得到标题
        myViewHolder.title_product.setText(listBeans.get(i).getTitle());
        //得到价格
        myViewHolder.price_product.setText(listBeans.get(i).getPrice()+"");

        //根据我记录的状态，改变勾选
        myViewHolder.check_product.setChecked(listBeans.get(i).isCheck());

        //商品的跟商品的有所不同，商品添加了选中改变的监听
        myViewHolder.check_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //有限改变自己的状态
                listBeans.get(i).setCheck(isChecked);
                //回调，
                if (mShopCallBackListener != null){
                    mShopCallBackListener.callBack();
                }
            }
        });
      /*  myViewHolder.customCounterView.setData(this,listBeans,i);
        myViewHolder.customCounterView.setCallBackListener(new CustomCounterView.CallBackListener() {
            @Override
            public void callBack() {
                if (mShopCallBackListener != null){
                    mShopCallBackListener.callBack();
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        //得到集合的长度
        return listBeans.size();
    }
    /**
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *根据 商家的CheckBox的 状态  改变商品的状态
     *整一个方法  参数为布尔值
     * @param
     */
    public void selectOrRemoveAll(boolean checked) {
        //遍历商品集合   得到所有的选中状态
        for (UserBean.DataBean.ListBean listBean : listBeans){
            listBean.setCheck(checked);
        }
        //刷新
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView image_product;
            TextView price_product,title_product;
            CheckBox check_product;
           // CustomCounterView customCounterView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_product = itemView.findViewById(R.id.image_product);
            price_product = itemView.findViewById(R.id.price_product);
            title_product = itemView.findViewById(R.id.title_product);
            check_product = itemView.findViewById(R.id.check_product);
            //customCounterView = itemView.findViewById(R.id.jiajiajianjian_zidingyibuju);

        }
    }

    //整一个接口回调
    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }
    //返回到  商品的适配器
    public interface ShopCallBackListener {
        void callBack();
    }
}
