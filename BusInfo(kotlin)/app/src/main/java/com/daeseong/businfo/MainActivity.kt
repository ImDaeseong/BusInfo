package com.daeseong.businfo

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val tag = MainActivity::class.java.simpleName

    private lateinit var include: View
    private lateinit var mainTabLayout: TabLayout
    private lateinit var mainViewPager: SwipeViewPager
    private var mainPagerAdapter: MainPagerAdapter? = null
    private var currentTabIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTitleBar()

        setContentView(R.layout.activity_main)

        init()

        setViewPager()
        setInitTabLayout()
    }

    private fun initTitleBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.rgb(255, 255, 255)
        }

        try {
            // 안드로이드 8.0 오레오 버전에서만 오류 발생
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        } catch (ex: Exception) {
        }
    }

    private fun init() {
        mainTabLayout = findViewById(R.id.maintabLayout)
        mainViewPager = findViewById(R.id.mainviewPager)
        include = findViewById(R.id.include_maintoolbar)

        val cLbusmenu: View = include.findViewById(R.id.cLbusmenu)
        cLbusmenu.setOnClickListener {
            try {
                Log.e(tag, "상단 메뉴 클릭")
            } catch (ex: Exception) {
            }
        }
    }

    private fun setViewPager() {
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        mainViewPager.adapter = mainPagerAdapter
        mainTabLayout.setupWithViewPager(mainViewPager)
    }

    private fun setInitTabLayout() {
        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                try {
                    currentTabIndex = tab.position
                    mainViewPager.currentItem = currentTabIndex
                } catch (ex: Exception) {
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        val viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                Log.e(tag, "onPageSelected:$position")
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        }
        mainViewPager.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    fun hideKeyboard() {
        try {
            currentFocus?.let {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
        } catch (ex: Exception) {
        }
    }
}
