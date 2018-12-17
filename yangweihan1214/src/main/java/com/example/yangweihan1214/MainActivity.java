package com.example.yangweihan1214;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yangweihan1214.Bean.UserBean;
import com.example.yangweihan1214.Presenter.IPresenterlmpl;
import com.example.yangweihan1214.View.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {
    private Button go_back;
    private TextView textView,t;
    private ImageView add_like;
    private IPresenterlmpl iPresenterlmpl;
    private XRecyclerView xRecyclerView;
    private Newsadapter newsadapter;
    private int pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initIPresenter();
        initXRecyclerView();
        getProductList();

        pager = 1 ;

        //点击图片
        add_like.setImageResource(R.drawable.kongxin);
        add_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator translationX = ObjectAnimator.ofFloat(add_like, "translationX", 0,-620,0);
                ObjectAnimator translationY = ObjectAnimator.ofFloat(add_like, "translationY", 0, 1050,0);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(add_like, "alpha", 1f, 0f,1f);
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(add_like, "scaleX", 1f, 0f,1f);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(add_like, "scaleY", 1f, 0f,1f);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(2000);
                animatorSet.playTogether(translationX,translationY,alpha,scaleX,scaleY);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        add_like.setImageResource(R.drawable.shixin);
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();

            }
        });
    }


    private void initXRecyclerView() {
        xRecyclerView = findViewById(R.id.xrecyclerview);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pager = 1;
                getProductList();
            }

            @Override
            public void onLoadMore() {
                getProductList();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        newsadapter = new Newsadapter(this);
        xRecyclerView.setAdapter(newsadapter);
    }

    private void initIPresenter() {
        iPresenterlmpl = new IPresenterlmpl(this);

}

private void getProductList(){
    HashMap<String , String > map = new HashMap<>();
    iPresenterlmpl.startRequest(Apris.url,map,UserBean.class);
}
    private void initView() {
        go_back = findViewById(R.id.go_back);
        textView = findViewById(R.id.today_news);
        add_like = findViewById(R.id.add_like);
        t = findViewById(R.id.t);
    }

    @Override
    public void setRequsertData(Object data) {

            UserBean userBean = (UserBean) data;
            List<UserBean.DataBean> list = userBean.getData();
            Toast.makeText(MainActivity.this,list.get(0).getTitle(),Toast.LENGTH_SHORT).show();
            if (pager == 1){
                newsadapter.setmData(list);
            }else {
                newsadapter.addmData(list);
            }
            pager ++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
    }

    @Override
    public void setRequsertDataFali(Object data) {
        Exception exception = (Exception) data;
        exception.printStackTrace();
        Toast.makeText(MainActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
    }
}
