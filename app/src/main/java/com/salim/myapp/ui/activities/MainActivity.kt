package com.salim.myapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.salim.myapp.R
import com.salim.myapp.ui.fragments.DoneTasksFragment
import com.salim.myapp.ui.fragments.PendingTasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewPager(pager)
        tabs.setupWithViewPager(pager)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPager.adapter =
            ViewPagerAdapter(
                supportFragmentManager
            )
    }

    class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        val names = arrayOf("Pending", "Done")
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    val pendingFragment = PendingTasksFragment()
                    return pendingFragment
                }
                else -> {
                    val doneFragment = DoneTasksFragment()
                    return doneFragment
                }
            }
        }

        override fun getCount(): Int {
            return names.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return names[position]
        }
    }

}