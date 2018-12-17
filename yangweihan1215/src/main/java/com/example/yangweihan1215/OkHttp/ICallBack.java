package com.example.yangweihan1215.OkHttp;

import java.io.IOException;

public interface ICallBack {
    void setData(Object o);

    Void setFail(IOException msg);
}
