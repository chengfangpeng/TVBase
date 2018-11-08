package com.xray.tv.item;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author cfp
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/11/02
 * @changeRecord [修改记录] <br/>
 */
public class HostImageView extends AppCompatImageView {

    public HostImageView(Context context) {
        super(context);
        init();
    }

    public HostImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HostImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * init action
     */
    private void init(){
        setFocusable(true);
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if(gainFocus){
            setBackgroundColor(Color.BLUE);
        }else{
            setBackgroundColor(Color.GREEN);
        }
    }
}
