package com.example.yangweihan1217;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yangweihan1217.Adapter.NewsAdapter;
import com.example.yangweihan1217.Bean.UserBean;
import com.example.yangweihan1217.Prenter.IPresenterImpl;
import com.example.yangweihan1217.View.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {
    private XRecyclerView xRecyclerView;
    private EditText main_text;
    private Button qiehuan;
    private IPresenterImpl iPresenter;
    private String type = "笔记本";
    private int pager = 1;
    private NewsAdapter adapter ;
    private Boolean p = false;
    private int m = 2;
    private  List<UserBean.DataBean> userDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDataList = new ArrayList<>();
        initView();
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequesrData(String.format(Apis.showDataUrl,type,pager),UserBean.class);
        adapter = new NewsAdapter(this);
        xRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == true){
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    xRecyclerView.setLayoutManager(layoutManager);
                    qiehuan.setText("线性");
                    p = false;
                }else {
                    p = true;
                    qiehuan.setText("网格");
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,m);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    xRecyclerView.setLayoutManager(gridLayoutManager);
                }

            }
        });


        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pager = 1;
                iPresenter.startRequesrData(String.format(Apis.showDataUrl,type,pager),UserBean.class);
            }

            @Override
            public void onLoadMore() {
                iPresenter.startRequesrData(String.format(Apis.showDataUrl,type,pager),UserBean.class);
            }
        });
        adapter.setClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(final View view, final int pasition) {
                userDataList = adapter.getList();
                ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
                ObjectAnimator x = ObjectAnimator.ofFloat(view, "x", 1f, 0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(3400);
                animatorSet.playTogether(alpha,x);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        adapter.remove(pasition);

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
    }

    private void initView() {
        xRecyclerView = findViewById(R.id.xrcyclerView);
        main_text = findViewById(R.id.main_text);
        qiehuan = findViewById(R.id.qiehuan);
    }

    @Override
    public void setRequestData(Object data) {
        UserBean userBean = (UserBean) data;
        List<UserBean.DataBean> list = userBean.getData();
        if (pager == 1){
            adapter.setMlist(list);


        }else if (pager > 1){
            adapter.addMlist(list);

        }
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
    }
}
