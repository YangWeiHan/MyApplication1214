package com.example.yangweihan1214.Presenter;

import java.util.Map;

public interface IPresenter {

    void startRequest(String url, Map<String,String> params, Class clazz);
}
