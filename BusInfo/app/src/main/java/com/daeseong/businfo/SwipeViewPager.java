package com.daeseong.businfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

public class SwipeViewPager extends ViewPager {
    private boolean enabled;

    public SwipeViewPager(Context context) {
        super(context);
        this.enabled = true;
    }

    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.enabled) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

