package com.example.yangweihan1218.Presenter;

import java.util.Map;
/*
* presenter的接口
* */
public interface IPresenter {
    //参数为  接口，map集合  javaBean
    void startRequest(String url, Map<String,String> params ,Class clazz );
}
