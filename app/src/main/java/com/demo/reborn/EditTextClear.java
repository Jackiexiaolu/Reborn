package com.demo.reborn;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.demo.reborn.R;


public class EditTextClear extends AppCompatEditText {

    /**
     * 定义一键删除图标
     */

    private Drawable deleteDrawable;

    public EditTextClear(Context context){
        super(context);
        init();//在构造函数时初始化
    }

    public EditTextClear(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }


    public EditTextClear(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init();

    }

    public void init()
    {
        deleteDrawable = getResources().getDrawable(R.drawable.delete);
        setCompoundDrawablesWithIntrinsicBounds( null
                , null,
                deleteDrawable, null);
    }

    /**
     * 通过监听复写edittext本身的方法来确定是否显示图标
     * 监听方法：onTextChanged()和onFocusChanged()
     * 调用时刻：当输入框变化时 and 当焦点发生变化时
     */
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text,start,lengthBefore,lengthAfter);
        setClearIconVisible(hasFocus() && text.length() >0 );

    }
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused,direction,previouslyFocusedRect);
        setClearIconVisible(focused && length() >0 );
    }

    /**
     * 判断是否显示删除图标
     */
    public void setClearIconVisible(boolean visible){
         setCompoundDrawablesWithIntrinsicBounds(null,null,visible ? deleteDrawable:null,null);

    }

    /**
     * 对删除区域设置点击事件：点击=清空搜索内容
     * 当手指抬起的位置在删除图标的区域，即视为点击了图标要清空搜索内容
     */

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                Drawable drawable = deleteDrawable;
                if(drawable!=null && event.getX() <= (getWidth() - getPaddingRight())
                    && event.getX() >= (getWidth()-getPaddingRight()-drawable.getBounds().width()))
                        setText("");
                break;
        }
        return super.onTouchEvent(event);
    }



}



