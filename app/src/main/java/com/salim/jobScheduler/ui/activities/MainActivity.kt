package com.salim.jobScheduler.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.salim.jobScheduler.R
import com.salim.jobScheduler.ui.fragments.DoneTasksFragment
import com.salim.jobScheduler.ui.fragments.PendingTasksFragment
import com.salim.jobScheduler.utils.NavigationManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun loadLayoutResource(): Int = R.layout.activity_main

    lateinit var mNavManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigationManager(R.id.frameLayout)
        tabInit()

        ivLanguage.setOnClickListener {
            startActivity(Intent(this@MainActivity, LanguageActivity::class.java))
        }

    }

    protected fun initNavigationManager(@IdRes frameLayout: Int) {
        mNavManager = NavigationManager()
        mNavManager.init(supportFragmentManager, frameLayout)
    }

    private fun tabInit() {
        val tabOne = tabs.newTab()
        val tabTwo = tabs.newTab()
        tabOne.setText(R.string.pending)
        tabTwo.setText(R.string.finished)
        tabs.addTab(tabOne)
        tabs.addTab(tabTwo)


        tabSelect(tabs.getTabAt(0), true)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tabView: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tabView: TabLayout.Tab?) {
                tabSelect(tabView, false)

            }

            override fun onTabSelected(tabView: TabLayout.Tab?) {
                tabSelect(tabView, true)
            }
        })
    }


    private fun tabSelect(tabView: TabLayout.Tab?, select: Boolean) {
        if (select) {
            tabView?.view?.background =
                ContextCompat.getDrawable(this@MainActivity, R.drawable.tab_bg)
            openFragment(tabView!!.position)
        } else {
            tabView?.view?.background = null
        }
    }

    private fun openFragment(position: Int) {
        when (position) {
            0 -> mNavManager.openAsRoot(bundleFragment(PendingTasksFragment()))
            else -> mNavManager.openAsRoot(bundleFragment(DoneTasksFragment()))
        }
    }

    private fun bundleFragment(fragment: Fragment): Fragment {
        return fragment
    }
}