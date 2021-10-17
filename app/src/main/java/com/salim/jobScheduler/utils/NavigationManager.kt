package com.salim.jobScheduler.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by µðšţãƒâ ™ on 04/08/2020.
 *  ->
 */
class NavigationManager {
    enum class OpenMethod {
        ADD, ADD_TEMP, REPLACE
    }

    companion object {
        const val TEMP_FRAGMENT: String = "tempFragment"
    }

    interface NavigationListener {
        fun onBackStackChanged()
    }

    interface OnNavigatedListener {
        fun onNavigated(fragment: Fragment)
    }

    private var fragmentManager: FragmentManager? = null
    private var fragmentContainerId: Int = -1
    private var navigationListener: NavigationListener? = null

    private val navigationListeners = mutableListOf<OnNavigatedListener>()

    fun init(fragmentManager: FragmentManager, fragmentContainerId: Int) {
        this.fragmentManager = fragmentManager
        this.fragmentManager?.addOnBackStackChangedListener {
            navigationListener?.onBackStackChanged()

            navigationListeners.forEach(::notifyNavigated)
        }
        this.fragmentContainerId = fragmentContainerId
    }

    private fun notifyNavigated(listener: OnNavigatedListener) {
        topFragment()?.apply(listener::onNavigated)
    }

    fun addOnNavigatedListener(action: (Fragment) -> Unit) {
        navigationListeners.add(object : OnNavigatedListener {
            override fun onNavigated(fragment: Fragment) {
                action(fragment)
            }
        })
    }

    fun addOnNavigatedListener(listener: OnNavigatedListener) {
        navigationListeners.add(listener)
    }

    fun removeNavigationListener(navigationListener: OnNavigatedListener) {
        navigationListeners.remove(navigationListener)
    }

    fun setOnBackStackChangedListener(navigationListener: NavigationListener) {
        this.navigationListener = navigationListener
    }

    fun open(
        fragment: Fragment,
        containerId: Int = fragmentContainerId,
        openMethod: OpenMethod = OpenMethod.REPLACE,
        addToBackStack: Boolean = true,
        fragmentTag: String = ""
    ) {
        fragmentManager?.let {
            val transaction = it.beginTransaction()
            when (openMethod) {
                OpenMethod.REPLACE -> transaction.replace(containerId, fragment, fragmentTag)
                OpenMethod.ADD -> transaction.add(containerId, fragment, fragmentTag)
                OpenMethod.ADD_TEMP -> transaction.add(containerId, fragment, TEMP_FRAGMENT)
            }

            if (addToBackStack) {
                transaction.addToBackStack(fragmentTag)
            }

            transaction.commit()
        }
    }

    fun removeTempFragment() {
        fragmentManager?.popBackStackImmediate(
            TEMP_FRAGMENT,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

    }

    fun openAsRoot(fragment: Fragment, fragmentTag: String = "") {
        popEveryFragment()
        open(fragment, addToBackStack = false, fragmentTag = fragmentTag)
    }

    fun remove(fragment: Fragment?) {
        fragment?.let {
            fragmentManager?.beginTransaction()?.remove(it)?.commit()
        }
    }

    fun navigateBackStack(activity: Activity) {
        if (isRootFragmentVisible()) {
            activity.finish()
        } else {
            fragmentManager?.popBackStack()
        }
    }

    fun navigateBackStack(index: Int) {
        fragmentManager?.popBackStack()
    }

    fun isRootFragmentVisible() = (fragmentManager?.backStackEntryCount ?: 0) <= 1

    fun backStackCount(): Int = fragmentManager?.backStackEntryCount ?: 0

    fun topFragment(): Fragment? =
        fragmentManager?.findFragmentById(fragmentContainerId)

    fun getCurrentFragmentByID(fragmentContainerID: Int): Fragment? =
        fragmentManager?.findFragmentById(fragmentContainerID)

    private fun popEveryFragment() {
        fragmentManager?.let {
            val backStackCount = it.backStackEntryCount
            for (i in 0 until backStackCount) {
                val id = it.getBackStackEntryAt(i).id
                it.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    fun popToFragment(fragmentTag: String, inclusive: Boolean) {
        fragmentManager?.let {
            while (it.backStackEntryCount > 0) {
                if (fragmentTag != it.getBackStackEntryAt(it.backStackEntryCount - 1).name) {
                    it.popBackStackImmediate()

                } else {
                    if (inclusive) {
                        it.popBackStackImmediate()
                    }
                    break
                }
            }
        }
    }
}