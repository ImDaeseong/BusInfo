package com.daeseong.businfo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = MainPagerAdapter.class.getSimpleName();

    private String titles[] = {"나의경로", "버스조회", "즐겨찾기", "버스번호"};

    public MainPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainTab1Fragment();
            case 1:
                return new MainTab2Fragment();
            case 2:
                return new MainTab3Fragment();
            case 3:
                return new MainTab4Fragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
