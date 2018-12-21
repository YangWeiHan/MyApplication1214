package com.example.yangweihan1217.OKHttp;

import java.io.IOException;

public interface ICallBack {
    void setData(Object o);

    void setFail(IOException msg);
}
