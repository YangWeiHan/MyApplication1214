package com.example.yangweihan1218.Model;

import com.example.yangweihan1218.MyCallBack.MyCallBack;
import com.example.yangweihan1218.OkHttp.ICallBack;
import com.example.yangweihan1218.OkHttp.OkHttpUtils;

import java.util.Map;
//M层 的实现类
public class IModellmpl implements IModel {
    //参数里有一个接口回调  用于把请求过来的数据回调给P  层
    @Override
    public void request(String url, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        //调用OKHTTP的数据
        OkHttpUtils.getInstance().postEnqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object obj) {
                //请求过来的数据回调给P通过接口回调给P层
                myCallBack.success(obj);
            }

            @Override
            public void failed(Exception e) {
                myCallBack.failed(e);
            }
        });
    }
}
