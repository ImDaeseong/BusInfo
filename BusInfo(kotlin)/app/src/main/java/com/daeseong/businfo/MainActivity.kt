package com.daeseong.businfo


import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class MainActivity : AppCompatActivity() {

    private val tag = MainActivity::class.java.simpleName

    private var include: View? = null
    private var Main_tabLayout: TabLayout? = null
    private var Main_viewPager: SwipeViewPager? = null
    private var mainPagerAdapter: MainPagerAdapter? = null
    private var nCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitTitleBar()

        setContentView(R.layout.activity_main)

        init()

        setViewPager()
        setInitTabLayout()
    }

    private fun InitTitleBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.statusbar_bg)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun init() {

        //탭
        Main_tabLayout = findViewById<TabLayout>(R.id.maintabLayout)

        //하단 뷰 페이지
        Main_viewPager = findViewById<SwipeViewPager>(R.id.mainviewPager)
        include = findViewById(R.id.include_maintoolbar)

        val cLbusmenu = include!!.findViewById(R.id.cLbusmenu) as View
        cLbusmenu.setOnClickListener(object : OnSingleClickListener() {

            override fun onSingleClick(view: View) {
                try {

                } catch (ex: Exception) {
                    Log.e(tag, ex.message)
                }
            }
        })

    }

    private fun setViewPager() {
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        Main_viewPager!!.adapter = mainPagerAdapter
        Main_tabLayout!!.setupWithViewPager(Main_viewPager)
    }

    private fun setInitTabLayout() {

        Main_tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                try {

                    nCurrentIndex = tab.position
                    Main_viewPager!!.currentItem = nCurrentIndex
                } catch (ex: java.lang.Exception) {
                    Log.e(tag, ex.message)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        val viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {

            override fun onPageSelected(position: Int) {

                //Log.e(tag, "onPageSelected:$position")
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {}
        }
        Main_viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    fun hideKeyboard() {

        try {

            if (currentFocus != null) {
                val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }
        } catch (ex: java.lang.Exception) {
            Log.e(tag, ex.message)
        }
    }

}
