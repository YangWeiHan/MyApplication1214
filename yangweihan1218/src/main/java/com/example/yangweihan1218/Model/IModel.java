package com.example.yangweihan1218.Model;

import com.example.yangweihan1218.MyCallBack.MyCallBack;

import java.util.Map;
//M层的接口
public interface IModel {
        //这里面要有一个接口  通过这个接口把从网络上请求出来的数据回调给 P层的实现类
        void request(String url , Map<String,String> params,Class clazz, MyCallBack myCallBack);
}
