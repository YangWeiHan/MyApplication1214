package com.example.yangweihan1214.CallBack;

public interface MyCallBack<T> {
    void success(T data);
    void failed(Exception e);
}
