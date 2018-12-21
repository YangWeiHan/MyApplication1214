package com.example.yangweihan1218.ZDYView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class WeekFlowLayout extends LinearLayout {
    //设置子布局中做高的那个
    private int mChildMaxHeight;
    //每一个子布局上下的间距
    private int mVSpace = 20;
    //每一个子布局左右的间距
    private int mHSpace = 20;

    public WeekFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //拿到父级容器推荐的宽高以及计算模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //测量子布局的大小
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //找到子布局中最高的
        findMaxChildMaxHeight();
        //初始化
        int left = 0, top = 0;
        //循环
        int childCount = getChildCount();
        for (int i = 0; i <childCount ; i++) {
            View view = getChildAt(i);
            //是否是 一行的 开头
            if (left != 0){
                //如果子布局的宽度都比父级的宽的大了
                //那就说明 该换行了
                if ((left + view.getMeasuredWidth()) > sizeWidth){
                    //计算出下一行的top
                    top += mChildMaxHeight+mVSpace;
                    //设置为初始值
                    left = 0;
                }
            }
            //对View上的内容进行测量后得到的View内容占据的宽度
            left += view.getMeasuredWidth()+mHSpace;
        }
        setMeasuredDimension(sizeWidth,(top + mChildMaxHeight) > sizeHeight ? sizeHeight : top + mChildMaxHeight);

    }
    //布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        findMaxChildMaxHeight();

        //初始化
        int left = 0,top = 0;
        //开始安排子布局的 位置
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (left != 0){
                //换行了
                if ((left + view.getMeasuredWidth()) > getWidth()){
                    //计算下一行的top
                    top += mChildMaxHeight + mVSpace;
                    left = 0;
                }
            }
            view.layout(left,top,left+view.getMeasuredWidth() ,top + mChildMaxHeight);
            left += view.getMeasuredWidth() + mHSpace;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    //找到子布局中最高的
    private void findMaxChildMaxHeight(){
        //初始值为0
        mChildMaxHeight = 0;
        //得到所有的子布局
        int childCount = getChildCount();
        //循环搜索看看谁那个子布局最高
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view.getMeasuredHeight() > mChildMaxHeight){
                mChildMaxHeight = view.getMeasuredHeight();
            }
        }
    }

}
