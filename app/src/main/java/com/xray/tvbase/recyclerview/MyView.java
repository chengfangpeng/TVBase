package com.xray.tvbase.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.airbnb.epoxy.ModelView;
import com.airbnb.epoxy.TextProp;

/**
 * @author cfp
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/11/08
 * @changeRecord [修改记录] <br/>
 */
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
public class MyView extends FrameLayout {
    public MyView(@NonNull Context context) {
        super(context);
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TextProp
    public void setTitle(String text){
        setBackgroundColor(Color.BLUE);
    }

}
