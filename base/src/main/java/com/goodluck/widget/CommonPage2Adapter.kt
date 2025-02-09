package com.goodluck.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by zf on 2020/3/27
 * 描述：
 */
class CommonPage2Adapter(activity: FragmentActivity, fragments: ArrayList<out Fragment>) :
    FragmentStateAdapter(activity) {
    var mFragments = fragments
    override fun getItemCount(): Int = mFragments.size

    override fun createFragment(position: Int): Fragment = mFragments[position]


}