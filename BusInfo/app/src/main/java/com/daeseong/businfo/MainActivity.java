package com.daeseong.businfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
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
    private TabLayout Main_tabLayout;
    private SwipeViewPager Main_viewPager;
    private MainPagerAdapter mainPagerAdapter = null;
    private int nCurrentIndex = 0;


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
            window.setStatusBarColor(getResources().getColor(R.color.statusbar_bg));
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void init(){

        //탭
        Main_tabLayout = (TabLayout)findViewById(R.id.maintabLayout);

        //하단 뷰 페이지
        Main_viewPager = (SwipeViewPager) findViewById(R.id.mainviewPager);

        include = findViewById(R.id.include_maintoolbar);

        View cLbusmenu = (View) include.findViewById(R.id.cLbusmenu);
        cLbusmenu.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {

                try {
                    //startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }catch (Exception ex){
                    Log.e(TAG, ex.getMessage());
                }

            }
        });
    }

    private void setViewPager(){
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        Main_viewPager.setAdapter(mainPagerAdapter);
        Main_tabLayout.setupWithViewPager(Main_viewPager);
    }

    private void setInitTabLayout(){

        Main_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                try {
                    nCurrentIndex = tab.getPosition();
                    Main_viewPager.setCurrentItem(nCurrentIndex);
                }catch (Exception ex){
                    Log.e(TAG, ex.getMessage());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        SwipeViewPager.OnPageChangeListener viewPagerPageChangeListener  = new SwipeViewPager.OnPageChangeListener(){
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
        Main_viewPager.addOnPageChangeListener(viewPagerPageChangeListener );
    }

    public void hideKeyboard() {

        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception ex){
            Log.e(TAG, ex.getMessage());
        }

    }
}
