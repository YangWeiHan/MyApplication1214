package com.example.yangweihan1217.MyCallBack;

public interface MyCallBack<T> {
    void setData(T data);
    void setFail(String msg);
}
