package com.daeseong.businfo;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BusApplication extends Application {

    private static final String TAG = BusApplication.class.getSimpleName();

    public String API_Key ="%2FSWbuoncrZtSM3DaBUA4PJVxqJMFKs0Eu%2F%2FzgFQf8dvVjzIi8ESOjmRaQtAkLKoQUS3S%2BZy%2FwLwR08%2BCT9BWuA%3D%3D";

    private static BusApplication mInstance;
    public static synchronized BusApplication getInstance() {
        return mInstance;
    }

    private static Context mContext;
    public static Context getAppContext(){
        return mContext;
    }

    private static Toast toast;
    public static void showToast(String sMsg, boolean isLengthLong) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_layout, null);

        TextView tvToast = view.findViewById(R.id.tvtoast);
        tvToast.setMaxLines(1);
        tvToast.setTextColor(Color.parseColor("#000000"));
        tvToast.setText(sMsg);

        toast = new Toast(mContext);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(isLengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public static void cancelToast() {
        try {
            if (toast != null) {
                toast.cancel();
            }
        } catch (Exception e) {
        }
    }

    private ClipboardManager clipboardManager;
    private ClipData clipData;

    public void setClipboardText(String input) {
        try {
            if (clipboardManager != null) {
                clipData = ClipData.newPlainText("text", input);
                clipboardManager.setPrimaryClip(clipData);
            }
        } catch (Exception ex) {
        }
    }

    public String getClipboardText() {
        String data = "";
        try {
            if (clipboardManager != null && clipboardManager.hasPrimaryClip()) {
                clipData = clipboardManager.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                data = item.getText().toString();

                if (!String_util.isNumeric(data)) {
                    data = "";
                }
            }
        } catch (Exception ex) {
        }
        return data;
    }

    public void clearClipboardText() {
        try {
            if (clipboardManager != null) {
                ClipData data = clipData.newPlainText("", "");
                clipboardManager.setPrimaryClip(data);
            }
        } catch (Exception ex) {
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = getApplicationContext();

        try {
            clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        } catch (Exception e) {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
