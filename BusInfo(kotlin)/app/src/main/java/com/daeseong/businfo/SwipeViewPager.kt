package com.daeseong.businfo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class SwipeViewPager : ViewPager {
    private var isPagingEnabled: Boolean = true

    constructor(context: Context) : super(context) {
        this.isPagingEnabled = true
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.isPagingEnabled = true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(ev)
    }

    fun setPagingEnabled(isPagingEnabled: Boolean) {
        this.isPagingEnabled = isPagingEnabled
    }
}
