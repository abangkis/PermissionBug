package net.mreunionlabs.permissionbug.ui

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_map_tab.*
import net.mreunionlabs.permissionbug.R
import net.mreunionlabs.permissionbug.adapter.BuswayFragmentPagerAdapter
import net.mreunionlabs.permissionbug.ui.fragment.BwMapFragment3
import net.mreunionlabs.permissionbug.ui.fragment.OurMapFragment
import java.util.*

class MapTabActivity : AppCompatActivity(), OurMapFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_tab)

        initTabs()
    }

    private fun initTabs() {
        val fragments = ArrayList<String>()

        fragments.add(OurMapFragment::class.java.name)
        fragments.add(BwMapFragment3::class.java.name)

        val adapter = BuswayFragmentPagerAdapter(supportFragmentManager, this@MapTabActivity, fragments)

        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
