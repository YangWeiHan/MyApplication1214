package com.example.yangweihan1215.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yangweihan1215.Bean.UserBean;
import com.example.yangweihan1215.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<UserBean.DataBean> mlist;

    public List<UserBean.DataBean> getMlist() {
        return mlist;
    }

    public UserAdapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }
    //下拉
    public void setMlist(List<UserBean.DataBean> mlist) {
        if (mlist != null){
            this.mlist = mlist;
        }
        notifyDataSetChanged();
    }
    //加载
    public void addMlist(List<UserBean.DataBean> mlist) {
            if (mlist != null){
                this.mlist = mlist;
            }
            notifyDataSetChanged();
        }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,viewGroup,false);
        return new ViewHolder(view);
    }

    public void remove(int pasition) {
        mlist.remove(pasition);
        notifyDataSetChanged();
    }

    //点击、长按监听  设置接口
    public interface OnItemClickListener{
        void onItemClick(View itemView,int pasition);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(int pasition,String title);
    }
    //私有化
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }

    //监听事件的构造方法
    public UserAdapter(OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.title.setText(mlist.get(i).getTitle());
        holder.price.setText(mlist.get(i).getPrice()+"");
        //截取字符串
        List<String> imgs = new ArrayList<>();

        String images = mlist.get(i).getImages();

        Pattern pent = Pattern.compile("\\|");

        String[] split = pent.split(images);

        Glide.with(context).load(split[0]).into(holder.image_icon);

        //点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null){
                    clickListener.onItemClick(v,i);
                }
            }
        });

        //长按
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null){
                    longClickListener.onItemLongClick(i,mlist.get(i).getTitle());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,price;
        ImageView image_icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.show_title);
            price = itemView.findViewById(R.id.show_price);
            image_icon = itemView.findViewById(R.id.image_icon);
        }
    }
}
