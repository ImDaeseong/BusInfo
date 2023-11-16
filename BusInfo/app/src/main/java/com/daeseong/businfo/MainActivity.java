package com.daeseong.businfo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private View include;
    private TabLayout mainTabLayout;
    private SwipeViewPager mainViewPager;
    private MainPagerAdapter mainPagerAdapter = null;
    private int currentTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitTitleBar();

        setContentView(R.layout.activity_main);

        init();

        setViewPager();
        setInitTabLayout();
    }

    private void InitTitleBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(255, 255, 255));
        }

        try {
            //안드로이드 8.0 오레오 버전에서만 오류 발생
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception ex) {
        }
    }

    private void init(){

        mainTabLayout = findViewById(R.id.maintabLayout);
        mainViewPager = findViewById(R.id.mainviewPager);
        include = findViewById(R.id.include_maintoolbar);

        View cLbusmenu = include.findViewById(R.id.cLbusmenu);
        cLbusmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.e(TAG, "상단 메뉴 클릭");
                } catch (Exception ex) {
                }
            }
        });
    }

    private void setViewPager(){
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(mainPagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }

    private void setInitTabLayout(){

        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    currentTabIndex = tab.getPosition();
                    mainViewPager.setCurrentItem(currentTabIndex);
                } catch (Exception ex) {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        SwipeViewPager.OnPageChangeListener viewPagerPageChangeListener = new SwipeViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected:" + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        mainViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    public void hideKeyboard() {

        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception ex) {
        }
    }
}
