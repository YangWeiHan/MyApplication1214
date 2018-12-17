package com.example.yangweihan1214.Model;

import com.example.yangweihan1214.CallBack.MyCallBack;

import java.util.Map;

public interface IModel {
    void requsertDate(String url , Map<String,String> params , Class clazz, MyCallBack myCallBack);
}
