package com.example.yangweihan1218.MyCallBack;

public interface MyCallBack<T> {
    void success(T data);
    void failed(Exception e);
}
