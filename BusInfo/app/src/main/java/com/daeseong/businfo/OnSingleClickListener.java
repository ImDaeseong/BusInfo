package com.daeseong.businfo;

import android.view.View;

public abstract class OnSingleClickListener implements View.OnClickListener {
    private static final long DEFAULT_DELAY_TIME = 500;

    private long lastClickTime = 0;
    private long delayTime;

    public OnSingleClickListener() {
        this(DEFAULT_DELAY_TIME);
    }

    public OnSingleClickListener(long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void onClick(View view) {
        if ((System.currentTimeMillis() - lastClickTime) < delayTime) {
            return;
        }

        onSingleClick(view);
        lastClickTime = System.currentTimeMillis();
    }

    public abstract void onSingleClick(View view);
}
