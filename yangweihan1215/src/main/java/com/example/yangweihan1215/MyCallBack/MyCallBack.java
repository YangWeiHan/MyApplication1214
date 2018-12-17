package com.example.yangweihan1215.MyCallBack;

public interface MyCallBack<T> {

    void setData(T data);

    void setFail(String msg);
}
