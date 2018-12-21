package com.example.yangweihan1217.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yangweihan1217.Bean.UserBean;
import com.example.yangweihan1217.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private List<UserBean.DataBean> list;

    public List<UserBean.DataBean> getList() {
        return list;
    }

    public NewsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setMlist(List<UserBean.DataBean> mlist) {
        if (list != null){
            this.list = mlist;
        }
        notifyDataSetChanged();
    }
    public void addMlist(List<UserBean.DataBean> mlist) {
        if (list != null){
            this.list = mlist;
        }
        notifyDataSetChanged();
    }
    public void remove(int pasition){
        list.remove(pasition);
        notifyDataSetChanged();
}
    @Override
    public int getItemCount() {
        return list.size();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,viewGroup,false);
        return new ViewHolder(view);
    }

    public interface OnItemClickListener{
        void OnItemClickListener(View view , int pasition );
    }
    public interface OnItemLongClickListener{
        void OnItemLongClickListener(  int pasition,String title );
    }
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public NewsAdapter(OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.subhead.setText(list.get(i).getSubhead());
        holder.prices.setText(list.get(i).getPrice()+"");

        String images = list.get(i).getImages();

        Pattern patt = Pattern.compile("\\|");

        String[ ] spils = patt.split(images);

        Glide.with(context).load(spils[0]).into(holder.image_icon);

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_icon;
        TextView subhead,prices;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_icon = itemView.findViewById(R.id.image_icon);
            subhead = itemView.findViewById(R.id.subhead);
            prices = itemView.findViewById(R.id.prices);
        }
    }
}
