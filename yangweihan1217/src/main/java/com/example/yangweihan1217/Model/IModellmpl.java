package com.example.yangweihan1217.Model;

import com.example.yangweihan1217.Bean.UserBean;
import com.example.yangweihan1217.MyCallBack.MyCallBack;
import com.example.yangweihan1217.OKHttp.ICallBack;
import com.example.yangweihan1217.OKHttp.OKHttpUtils;

import java.io.IOException;

public class IModellmpl implements  IModel {
    @Override
    public void ruqueData(String url, Class clazz, final MyCallBack myCallBack) {
        OKHttpUtils.getInstance().getEnqueue(url, UserBean.class, new ICallBack() {
            @Override
            public void setData(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void setFail(IOException msg) {

            }
        });

    }
}
