package com.example.yangweihan1215.OkHttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    //创建单例模式
    public static OkHttpUtils instance;

    private OkHttpClient mClient;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    //构造方法
    public static OkHttpUtils getInstance(){
        if (instance == null){
            synchronized (OkHttpUtils.class){
                if (null == instance){
                    instance = new OkHttpUtils();
                }
            }
        }
        return instance;
    }

    //用get 的异步请求

    private OkHttpUtils(){
        //拦截器日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //构造方法用建造者模式
        mClient = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        }
        public void getEnqueue(String url, final Class clazz, final ICallBack iCallBack){
            Request request = new Request.Builder().get().url(url).build();
            Call call = mClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallBack.setFail(e);
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response){
                    try {
                        String result = response.body().string();
                        Gson gson = new Gson();
                        final Object o = gson.fromJson(result, clazz);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                iCallBack.setData(o);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
