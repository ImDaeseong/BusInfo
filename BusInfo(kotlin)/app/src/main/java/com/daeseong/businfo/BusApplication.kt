package com.daeseong.businfo


import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast


class BusApplication : Application() {

    private val tag: String = BusApplication::class.java.simpleName

    companion object {
        private lateinit var mContext: Context
        private lateinit var mInstance: BusApplication

        fun getContext(): Context {
            return mContext.applicationContext
        }

        fun getInstance(): BusApplication {
            return mInstance
        }
    }

    var API_Key = "api key"

    private var clipboardManager: ClipboardManager? = null
    private var clipData: ClipData? = null

    private var toast: Toast? = null

    override fun onCreate() {
        super.onCreate()

        mContext = this
        mInstance = this

        try {
            clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        } catch (e: Exception) {
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    fun Toast(sMsg: String?, bLengthLong: Boolean) {

        val inflater = mContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.toast_layout, null)
        val tvtoast: TextView = view.findViewById(R.id.tvtoast)

        //최대 1줄까지
        tvtoast.maxLines = 1
        tvtoast.setTextColor(Color.parseColor("#000000"))
        tvtoast.text = sMsg
        toast = Toast(mContext)
        toast!!.setGravity(Gravity.BOTTOM, 0, 0)

        if (bLengthLong) {
            toast!!.duration = Toast.LENGTH_LONG
        } else {
            toast!!.duration = Toast.LENGTH_SHORT
        }

        toast!!.view = view
        toast!!.show()
    }

    fun Toastcancel() {

        try {
            toast!!.cancel()
        } catch (e: java.lang.Exception) {
        }
    }

    fun SetClipboardText(sInput: String?) {

        try {
            clipData = ClipData.newPlainText("text", sInput)
            clipboardManager!!.setPrimaryClip(clipData!!)
        } catch (ex: java.lang.Exception) {
        }
    }

    fun GetClipboardText(): String? {

        var sData = ""
        try {
            if (clipboardManager!!.hasPrimaryClip()) {
                clipData = clipboardManager!!.primaryClip
                val item = clipData!!.getItemAt(0)
                sData = item.text.toString()

                if (!String_util.isNumeric(sData)) {
                    sData = ""
                }
            }
        } catch (ex: java.lang.Exception) {
        }
        return sData
    }

    fun ClearClipboardText() {

        try {
            val data = ClipData.newPlainText("", "")
            clipboardManager!!.setPrimaryClip(data)
        } catch (ex: java.lang.Exception) {
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}