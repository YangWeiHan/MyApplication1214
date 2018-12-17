package com.example.yangweihan1215;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yangweihan1215.Adapter.UserAdapter;
import com.example.yangweihan1215.Bean.UserBean;
import com.example.yangweihan1215.Presenter.IPresenterlmpl;
import com.example.yangweihan1215.View.IView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {
        private Button qiehuan;
        private TextView shou;
        private XRecyclerView xrecyclerView;
        private IPresenterlmpl iPresenterlmpl;
        private String type = "笔记本";
        private int paget = 1;
        private UserAdapter adapter;
        private final int mSpanCount = 2;
        private List<UserBean.DataBean> dataBeanList;
        private boolean p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initIPresenter();
        xrecyclerView = findViewById(R.id.xrecyclerView);
        //拼接字符串
        iPresenterlmpl.startRequest(String.format(Apis.ShowDataurl,type,paget),UserBean.class);
        //管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerView.setLayoutManager(layoutManager);
        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == true){
                    //线性布局
                    Toast.makeText(MainActivity.this,"这个是线性布局！！！",Toast.LENGTH_SHORT).show();
                    qiehuan.setText("网格");
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    xrecyclerView.setLayoutManager(layoutManager);
                    p =false;
                }else {
                    Toast.makeText(MainActivity.this,"这个是网格布局！！！",Toast.LENGTH_SHORT).show();
                    qiehuan.setText("线性");
                    p = true;
                    //网格布局
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,mSpanCount);
                    gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                    xrecyclerView.setLayoutManager(gridLayoutManager);
                }
            }
        });
        adapter = new UserAdapter(this);
        //设置适配器
        xrecyclerView.setAdapter(adapter);
        //设置xRecyclerView  支持上拉刷新 下拉加载
        xrecyclerView.setPullRefreshEnabled(true);
        xrecyclerView.setLoadingMoreEnabled(true);
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                paget = 1;
                iPresenterlmpl.startRequest(String.format(Apis.ShowDataurl,type,paget),UserBean.class);
            }

            @Override
            public void onLoadMore() {
                paget++;
                iPresenterlmpl.startRequest(String.format(Apis.ShowDataurl,type,paget),UserBean.class);
            }
        });

        adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View itemView, final int pasition) {
                final float x1 = itemView.getX();
                dataBeanList = adapter.getMlist();
                ObjectAnimator alpha = ObjectAnimator.ofFloat(itemView, "alpha", 1f, 0f);
                ObjectAnimator x = ObjectAnimator.ofFloat(itemView, "X", 0, -800);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(3000);
                animatorSet.playTogether(alpha,x);
                animatorSet.start();
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        adapter.remove(pasition);
//                        dataBeanList.remove(pasition);
//                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"删除",Toast.LENGTH_SHORT).show();
                        itemView.setX(x1);
                        itemView.setAlpha(1.0f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


            }
        });

        adapter.setOnItemLongClickListener(new UserAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int pasition,String a) {
                Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initIPresenter() {
        iPresenterlmpl = new IPresenterlmpl(this);
    }

    //获取布局的资源ID
    private void initView() {
        qiehuan = findViewById(R.id.qiehuan);
        shou = findViewById(R.id.shou);
    }

    @Override
    public void setRequestData(Object data) {
        UserBean userBean = (UserBean) data;
        List<UserBean.DataBean> dataBean = userBean.getData();
        Toast.makeText(MainActivity.this,dataBean.get(0).getTitle(),Toast.LENGTH_LONG).show();
        if (paget == 1){
            adapter.setMlist(dataBean);
            //刷新停止
            xrecyclerView.refreshComplete();
        }else {
            //加载停止
            adapter.addMlist(dataBean);
            xrecyclerView.loadMoreComplete();
        }
    }
}
