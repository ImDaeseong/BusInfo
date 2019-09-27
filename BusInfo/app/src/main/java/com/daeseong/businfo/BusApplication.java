package com.daeseong.businfo;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BusApplication extends Application {

    private static final String TAG = BusApplication.class.getSimpleName();

    public String API_Key = "api key";

    private static BusApplication mInstance;
    public static synchronized BusApplication getInstance() {
        return mInstance;
    }

    private static Context mContext;
    public static Context getAppContext(){
        return mContext;
    }


    private static Toast toast;
    public static void Toast(String sMsg, boolean bLengthLong) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_layout, null);

        TextView tvtoast = view.findViewById(R.id.tvtoast);

        //최대 1줄까지
        tvtoast.setMaxLines(1);
        tvtoast.setTextColor(Color.parseColor("#000000"));
        tvtoast.setText(sMsg);

        toast = new Toast(mContext);
        toast.setGravity(Gravity.BOTTOM, 0, 0);

        if(bLengthLong) {
            toast.setDuration(Toast.LENGTH_LONG);
        }else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        toast.setView(view);
        toast.show();
    }

    public static void Toastcancel(){
        try {
            toast.cancel();
        }catch (Exception e){
        }
    }

    private ClipboardManager clipboardManager;
    private ClipData clipData;

    public void SetClipboardText(String sInput){
        try {
            clipData = ClipData.newPlainText("text", sInput);
            clipboardManager.setPrimaryClip(clipData);
        }catch (Exception ex){
        }
    }

    public String GetClipboardText(){
        String sData = "";
        try {
            if (clipboardManager.hasPrimaryClip()) {
                clipData = clipboardManager.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                sData = item.getText().toString();

                if(!String_util.isNumeric(sData)){
                    sData = "";
                }
            }
        }catch (Exception ex){
        }
        return sData;
    }

    public void ClearClipboardText()
    {
        try {
            ClipData data = clipData.newPlainText("", "");
            clipboardManager.setPrimaryClip(data);
        }catch (Exception ex){
        }
    }

    public static boolean isNetworkAvailable(Context context)
    {
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

        }catch (Exception e){
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
