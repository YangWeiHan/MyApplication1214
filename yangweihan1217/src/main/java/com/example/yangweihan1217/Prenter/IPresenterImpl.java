package com.example.yangweihan1217.Prenter;

import com.example.yangweihan1217.Model.IModellmpl;
import com.example.yangweihan1217.MyCallBack.MyCallBack;
import com.example.yangweihan1217.View.IView;

public class IPresenterImpl implements IPresent {
    private IModellmpl iModellmpl;
    private IView iView;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModellmpl = new IModellmpl();
    }

    public void onDecahe(){
        if (iView != null){
            iView = null;
        }
        if (iModellmpl != null ){
            iModellmpl = null;
        }
    }
    @Override
    public void startRequesrData(String url, Class clazz) {
        iModellmpl.ruqueData(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                iView.setRequestData(data);
            }

            @Override
            public void setFail(String msg) {

            }
        });

    }
}
