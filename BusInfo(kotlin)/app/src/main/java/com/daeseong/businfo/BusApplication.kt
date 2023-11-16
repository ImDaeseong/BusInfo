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
import android.widget.TextView
import android.widget.Toast

class BusApplication : Application() {

    companion object {

        private lateinit var mInstance: BusApplication
        private lateinit var mContext: Context
        private var toast: Toast? = null

        private lateinit var clipboardManager: ClipboardManager
        private lateinit var clipData: ClipData

        @JvmStatic
        fun getInstance(): BusApplication {
            return mInstance
        }
    }

    val API_Key = "%2FSWbuoncrZtSM3DaBUA4PJVxqJMFKs0Eu%2F%2FzgFQf8dvVjzIi8ESOjmRaQtAkLKoQUS3S%2BZy%2FwLwR08%2BCT9BWuA%3D%3D"

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        mContext = applicationContext

        try {
            clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        } catch (e: Exception) {
        }
    }

    fun showToast(sMsg: String, isLengthLong: Boolean) {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.toast_layout, null)

        val tvToast = view.findViewById<TextView>(R.id.tvtoast)
        tvToast.maxLines = 1
        tvToast.setTextColor(Color.parseColor("#000000"))
        tvToast.text = sMsg

        toast = Toast(mContext)
        toast?.apply {
            setGravity(Gravity.BOTTOM, 0, 0)
            duration = if (isLengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            view = view
            show()
        }
    }

    fun cancelToast() {
        try {
            toast?.cancel()
        } catch (e: Exception) {
        }
    }

    fun setClipboardText(input: String) {
        try {
            clipData = ClipData.newPlainText("text", input)
            clipboardManager.setPrimaryClip(clipData)
        } catch (ex: Exception) {
        }
    }

    fun getClipboardText(): String {
        var data = ""
        try {
            if (clipboardManager.hasPrimaryClip()) {
                clipData = clipboardManager.primaryClip!!
                val item = clipData.getItemAt(0)
                data = item.text.toString()

                if (!String_util.isNumeric(data)) {
                    data = ""
                }
            }
        } catch (ex: Exception) {
        }
        return data
    }

    fun clearClipboardText() {
        try {
            clipData = ClipData.newPlainText("", "")
            clipboardManager.setPrimaryClip(clipData)
        } catch (ex: Exception) {
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}
