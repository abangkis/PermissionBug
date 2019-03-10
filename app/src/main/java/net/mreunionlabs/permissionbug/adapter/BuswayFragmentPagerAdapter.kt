package net.mreunionlabs.permissionbug.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class BuswayFragmentPagerAdapter(
    fm: FragmentManager,
    private val context: Context,
    private val fragments: List<String>
) :
    FragmentStatePagerAdapter(fm) {
    //    private int[] tabTitles = new int[]{ R.string.trans, R.string.feeder, R.string.trans_jabodetabek, R.string.news, R.string.info, R.string.tcares, R.string.map};
    private val tabTitles = arrayOf("map")

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        // fragment instantiate will create the fragment as if it's calling its empty constructor
        return Fragment.instantiate(context, fragments[position])
        //        return PageFragment.newInstance(position + 1);
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return tabTitles[position]
//        return context.getString(tabTitles[position])
    }
}