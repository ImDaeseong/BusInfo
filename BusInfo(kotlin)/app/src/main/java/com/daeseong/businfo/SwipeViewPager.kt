package com.daeseong.businfo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.viewpager.widget.ViewPager


class SwipeViewPager : ViewPager {

    private var enabled: Boolean? = false

    constructor(context: Context?) : super(context!!) {
        enabled = true
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        enabled = true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (this.enabled!!) {
            return super.onInterceptTouchEvent(ev);
        }
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (this.enabled!!) {
            return super.onTouchEvent(ev);
        }
        return false
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.enabled = enabled
    }
}