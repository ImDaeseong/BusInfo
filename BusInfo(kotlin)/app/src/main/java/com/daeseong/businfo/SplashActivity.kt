package com.daeseong.businfo

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class SplashActivity : AppCompatActivity() {

    private val tag = SplashActivity::class.java.simpleName

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTitleBar()

        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init() {
        val activityReference = WeakReference(this)

        handler.postDelayed({
            val activity = activityReference.get()
            activity?.run {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 1000)
    }

    private fun initTitleBar() {
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        } catch (ex: Exception) {
        }

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } catch (ex: Exception) {
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        return
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            handler.removeCallbacksAndMessages(null)
        } catch (ex: Exception) {
        }
    }
}
