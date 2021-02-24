package com.daeseong.businfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class MainPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val tag = MainPagerAdapter::class.java.simpleName

    companion object {
        private const val TAB_COUNT = 4
    }

    private val titles = arrayOf("나의경로", "버스조회", "즐겨찾기", "버스번호")
    private val viewFragment = arrayOfNulls<Fragment>(4)

    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> {
                viewFragment[0] = MainTab1Fragment()
                return viewFragment[0]!!
            }
            1 -> {
                viewFragment[1] = MainTab2Fragment()
                return viewFragment[1]!!
            }
            2 -> {
                viewFragment[2] = MainTab3Fragment()
                return viewFragment[2]!!
            }
            3 -> {
                viewFragment[3] = MainTab4Fragment()
                return viewFragment[3]!!
            }
        }
        return null!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }

    fun getFragment(position: Int): Fragment? {
        return viewFragment[position]
    }
}
