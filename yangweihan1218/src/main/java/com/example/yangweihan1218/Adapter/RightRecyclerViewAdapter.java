package com.example.yangweihan1218.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yangweihan1218.Bean.UserBean;
import com.example.yangweihan1218.R;

import java.util.ArrayList;
import java.util.List;

public class RightRecyclerViewAdapter extends RecyclerView.Adapter<RightRecyclerViewAdapter.RightViewHolder> {

    private Context context;
    private List<UserBean.DataBean.ListBean> mlist;
    //在这个构造方法中集合没有被当做成参数  所以  可以自接new 一个集合
    //他和购物车的是不一样的主要的区别还是  商品的集合有没有被当成参数
    public RightRecyclerViewAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    public void setMlist(List<UserBean.DataBean.ListBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //得到商品的布局 UI
        View view = View.inflate(context, R.layout.right_recyclerview,null);
        RightRecyclerViewAdapter.RightViewHolder rightViewHolder = new RightRecyclerViewAdapter.RightViewHolder(view);
        return rightViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RightViewHolder rightViewHolder, int i) {
        //拼接字符串   拿到图片的集合
        String url = mlist.get(i).getImages().split("\\|")[0].replace("Https","Http");
        /*
        * 第一：注意spilt 里的两个\\
        * 第二：在Gilde 的 load 的uri 在拿到集合的时候就已经得到了
        * */
        Glide.with(context).load(url).into(rightViewHolder.imageView);
        //设置textVIew
        rightViewHolder.textView.setText(mlist.get(i).getTitle());
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class RightViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.shop_image_right);
            textView = itemView.findViewById(R.id.shop_title_right);
        }
    }
}
