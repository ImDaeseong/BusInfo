package com.daeseong.businfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles = arrayOf("나의경로", "버스조회", "즐겨찾기", "버스번호")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainTab1Fragment()
            1 -> MainTab2Fragment()
            2 -> MainTab3Fragment()
            3 -> MainTab4Fragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return titles.size
    }
}
