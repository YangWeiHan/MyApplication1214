package com.example.yangweihan1215.Presenter;

import com.example.yangweihan1215.Model.IModellmpl;
import com.example.yangweihan1215.MyCallBack.MyCallBack;
import com.example.yangweihan1215.View.IView;

public class IPresenterlmpl implements IPresenter{

    private IView iView;
    private IModellmpl iModellmpl;

    public IPresenterlmpl(IView iView) {
        this.iView = iView;
        iModellmpl = new IModellmpl();
    }

    public void onDetach(){
        if (iModellmpl != null){
            iModellmpl = null;
        }
        if (iView != null){
            iView = null;
        }
    }
    @Override
    public void startRequest(String url, Class clazz) {
        iModellmpl.ruqusertData(url, clazz, new MyCallBack() {
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
