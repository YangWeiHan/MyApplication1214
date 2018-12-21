package com.example.yangweihan1218.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangweihan1218.Bean.UserBean;
import com.example.yangweihan1218.R;

import java.util.ArrayList;
import java.util.List;

public class LeftRecyclerViewAdapter extends RecyclerView.Adapter<LeftRecyclerViewAdapter.LeftViewHolder> {

    private Context context;
    private List<UserBean.DataBean> mlist;

    public LeftRecyclerViewAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    public void setMlist(List<UserBean.DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LeftViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    //获取布局
        View view = View.inflate(context,R.layout.left_recyclerview,null);
        LeftRecyclerViewAdapter.LeftViewHolder leftViewHolder = new LeftRecyclerViewAdapter.LeftViewHolder(view);
        return leftViewHolder;
    }
    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull LeftViewHolder leftViewHolder, final int i) {
        //得到商家的名
        leftViewHolder.textView.setText(mlist.get(i).getSellerName());
        //这里就是  这个布局的作用了
        leftViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null){
                    // 在点击的时候 拿到商家的索引和商家的ID
                    mOnClickListener.onClick(i,mlist.get(i).getSellerid());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        //得到商家集合的长度
        return mlist.size();
    }

    public class LeftViewHolder extends RecyclerView.ViewHolder {
        //拿到商家里的资源 这里把布局也拿过来是因为  这个布局里只有一个View
        //在做点击事件的时候也可以直接点击这个布局
        LinearLayout mLinearLayout;
        TextView textView;
        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.shop_type_name);
            mLinearLayout = itemView.findViewById(R.id.shop_type);
        }
    }

    //设置一个点击监听事件
    private onClickListener mOnClickListener;

    public void setOnClickListener(onClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
    //点击事件的一个几口
    public interface onClickListener{
        void onClick(int position,String cid);
    }
}
