package com.example.yangweihan1214;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yangweihan1214.Bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class Newsadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<UserBean.DataBean> mData;

    public Newsadapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setmData(List<UserBean.DataBean> Data) {
        if (mData != null){
            this.mData = Data;
        }
        notifyDataSetChanged();
    }


    public void addmData(List<UserBean.DataBean> Data) {
        if (mData != null){
            mData.addAll(Data);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = View.inflate(context,R.layout.news_item,null);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Glide.with(context).load(mData.get(i).getThumbnail_pic_s()).into(holder.thumbnail_pic_s);
        holder.news_title.setText(mData.get(i).getTitle());
        holder.news_date.setText(mData.get(i).getDate());
        holder.author_name.setText(mData.get(i).getAuthor_name());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail_pic_s;
        private TextView news_title,news_date, author_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail_pic_s = itemView.findViewById(R.id.thumbnail_pic_s);
            news_title = itemView.findViewById(R.id.news_title);
            news_date = itemView.findViewById(R.id.news_date);
            author_name = itemView.findViewById(R.id.author_name);
        }
    }
}
