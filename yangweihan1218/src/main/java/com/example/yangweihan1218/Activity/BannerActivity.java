package com.example.yangweihan1218.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yangweihan1218.Apis;
import com.example.yangweihan1218.Bean.UserBean;
import com.example.yangweihan1218.Constants;
import com.example.yangweihan1218.Presenter.IPresenterlmpl;
import com.example.yangweihan1218.R;
import com.example.yangweihan1218.View.IView;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BannerActivity extends AppCompatActivity implements IView {
    private IPresenterlmpl iPresenterlmpl;
    private FlyBanner fly_Banner;
    private ImageView imageView;
    private List<String> image_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        iPresenterlmpl = new IPresenterlmpl(this);
        fly_Banner = findViewById(R.id.fly_banner);
        //属性动画
        getData();
        //设置动画
        imageAmintor();
    }

    private void imageAmintor() {
        imageView = findViewById(R.id.image_Animtor);
        //设置属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotationY", 0, 360);
       /* AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);*/
        animator.setDuration(3000);
        animator.setRepeatCount(-1);
        animator.start();

    }
    //一定要先找到接口  下面请求多来数据但是没有接口  （×）
    private void getData() {
        //得到Map集合
        //存放 两个参数  UID  和   71
        Map<String,String> map = new HashMap<>();
        map.put(Constants.MAP_KEY_GET_PRODUCT_UID,"71");
        iPresenterlmpl.startRequest(Apis.shopCarUrl,map,UserBean.class);
    }
    @Override
    public void showResponseData(Object data) {
        image_list = new ArrayList<>();
        //得到商家的集合
        UserBean userBean = (UserBean) data;
        List<UserBean.DataBean> data1 = userBean.getData();
        //先遍历商家，得到所有的商品
        for (int i = 0; i < data1.size(); i++) {
            //拿到所有商品
            List<UserBean.DataBean.ListBean> list = data1.get(i).getList();
            //遍历所有商品
            for (int j = 0; j <list.size(); j++) {
                //那都所有商品的图片
                String images = list.get(j).getImages();
                //分割图片
                Pattern pattern = Pattern.compile("\\|");
                //把所有的图片放到一个数组里
                String[] split = pattern.split(images);
                //遍历数组里的所有图片
                for (int k = 0; k < split.length; k++) {
                    //添加到本地集合里面
                    image_list.add(split[k]);
                }
            }
        }
        //把图片集合里的值赋给flyBanner 就OK
        fly_Banner.setImagesUrl(image_list);



    }

    @Override
    public void showResponseFail(Object data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenterlmpl.onDetach();;
    }
}
