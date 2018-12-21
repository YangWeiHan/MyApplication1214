package com.example.yangweihan1218.View;
//IView层  的接口
public interface IView<T> {
    //获取数据
    void showResponseData(T data);
    //获取数据失败
    void showResponseFail(T data);
}
