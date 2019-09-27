package com.daeseong.businfo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = MainPagerAdapter.class.getSimpleName();

    private static final int TAB_COUNT = 4;
    private String titles[] = {"나의경로", "버스조회", "즐겨찾기", "버스번호"};
    private Fragment[] viewFragment = new Fragment[4];

    public MainPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                viewFragment[0] = new MainTab1Fragment();
                return viewFragment[0];
            }
            case 1: {
                viewFragment[1] = new MainTab2Fragment();
                return viewFragment[1];
            }
            case 2: {
                viewFragment[2] = new MainTab3Fragment();
                return viewFragment[2];
            }
            case 3: {
                viewFragment[3] = new MainTab4Fragment();
                return viewFragment[3];
            }
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    public Fragment getFragment(int position){
        return viewFragment[position];
    }
}
