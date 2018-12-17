package com.example.yangweihan1215.Model;

import com.example.yangweihan1215.MyCallBack.MyCallBack;
import com.example.yangweihan1215.OkHttp.ICallBack;
import com.example.yangweihan1215.OkHttp.OkHttpUtils;
import com.example.yangweihan1215.View.IView;

import java.io.IOException;

public class IModellmpl implements IModel {


    @Override
    public void ruqusertData(String url, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getInstance().getEnqueue(url, clazz, new ICallBack() {
            @Override
            public void setData(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public Void setFail(IOException msg) {
                return null;
            }
        });
    }
}
