package com.daeseong.businfo

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    private val tag = SplashActivity::class.java.simpleName

    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitTitleBar()

        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {

        handler = Handler()
        handler!!.postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000)
    }

    private fun InitTitleBar() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onBackPressed() {
        //super.onBackPressed();
        return
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            if (handler != null) {
                handler!!.removeCallbacksAndMessages(null)
                handler = null
            }
        } catch (ex: Exception) {
            Log.e(tag, ex.message)
        }
    }
}
