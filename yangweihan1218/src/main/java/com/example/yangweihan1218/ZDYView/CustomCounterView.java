package com.example.yangweihan1218.ZDYView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yangweihan1218.Adapter.ProductsAdapter;
import com.example.yangweihan1218.Bean.UserBean;
import com.example.yangweihan1218.R;

import java.util.ArrayList;
import java.util.List;

public class CustomCounterView extends RelativeLayout implements View.OnClickListener {

    private EditText num_product;

    public CustomCounterView(Context context) {
        super(context);
        initView(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private Context context;

    private void initView(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.jiajiajianjian,null);
        Button jia_product = view.findViewById(R.id.jia_product);
        num_product = view.findViewById(R.id.num_product);
        Button jian_product = view.findViewById(R.id.jian_product);
        jia_product.setOnClickListener(this);
        jian_product.setOnClickListener(this);
        addView(view);
        num_product.setText(list.get(position).getNum());
        num_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private int num;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jia_product:
                //改变数量，设置数量 ，改变对象内容，回调，刷新布局
                num++;
                num_product.setText(num +"");
                list.get(position).setNum(num);
                callBackListener.callBack();
                productsAdapter.notifyDataSetChanged();
                break;
            case R.id.jian_product:
                if (num > 1){
                    num --;
                }else {
                    Toast.makeText(context,"没啦！！！",Toast.LENGTH_SHORT).show();
                }
                num_product.setText(num+"");
                list.get(position).setNum(num);
                callBackListener.callBack();
                productsAdapter.notifyDataSetChanged();
                break;
            default: break;
        }
    }
    private List<UserBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private ProductsAdapter productsAdapter;

    public void setData(ProductsAdapter productsAdapter , List<UserBean.DataBean.ListBean> list,int i ){
        this.list = list;
        this.productsAdapter = productsAdapter;
        position = i;
        num = list.get(i).getNum();
        num_product.setText(num+"");
    }


    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public interface CallBackListener{
        void callBack();
    }
}
