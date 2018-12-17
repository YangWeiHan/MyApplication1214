package com.example.yangweihan1214.Model;

import com.example.yangweihan1214.CallBack.MyCallBack;
import com.example.yangweihan1214.OkHttpUtils.ICallBack;
import com.example.yangweihan1214.OkHttpUtils.OkHttpUtils;

import java.util.Map;

public class IModellmpl implements IModel {
    @Override
    public void requsertDate(String url, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getmIntance().postEnqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object obj) {
                myCallBack.success(obj);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.failed(e);
            }
        });
    }
}
