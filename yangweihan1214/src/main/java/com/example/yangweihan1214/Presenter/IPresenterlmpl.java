package com.example.yangweihan1214.Presenter;

import com.example.yangweihan1214.CallBack.MyCallBack;
import com.example.yangweihan1214.Model.IModellmpl;
import com.example.yangweihan1214.View.IView;

import java.util.Map;

public class IPresenterlmpl implements IPresenter {
    private IModellmpl iModellmpl;
    private IView iView;

    public IPresenterlmpl(IView iView) {
        this.iView = iView;
        iModellmpl = new IModellmpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {
        iModellmpl.requsertDate(url, params, clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                iView.setRequsertData(data);
            }

            @Override
            public void failed(Exception e) {
                iView.setRequsertDataFali(e);
            }
        });
    }

    public void onDetach(){
        if (iModellmpl != null){
            iModellmpl = null;
        }
        if (iView != null){
            iView = null;
        }
    }
}
