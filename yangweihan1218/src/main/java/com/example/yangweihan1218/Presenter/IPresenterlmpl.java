package com.example.yangweihan1218.Presenter;

import com.example.yangweihan1218.Model.IModellmpl;
import com.example.yangweihan1218.MyCallBack.MyCallBack;
import com.example.yangweihan1218.View.IView;

import java.util.Map;

public class IPresenterlmpl implements IPresenter {
    //在P层里  要实例化M层的实现类和 V层的接口
    private IModellmpl iModellmpl;
    private IView iView;
    //构造方法
    public IPresenterlmpl(IView iView) {
        this.iView = iView;
        //new  一下
        iModellmpl = new IModellmpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {
        //从M层的实现类 用接口回调得到 M层请求过来的数据
        iModellmpl.request(url, params, clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                //V层 把数据在那过去
                iView.showResponseData(data);
            }

            @Override
            public void failed(Exception e) {
                iView.showResponseFail(e);
            }
        });
    }
    //解绑（为了解决内存溢出）
    public void onDetach(){
        if (iModellmpl != null){
            iModellmpl = null;
        }
        if (iView != null){
            iView = null;
        }
    }
}
