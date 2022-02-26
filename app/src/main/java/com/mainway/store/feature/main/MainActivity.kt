package com.mainway.store.feature.main

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController


import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import com.mainway.store.R

import com.mainway.store.common.setupWithNavController
import com.mainway.store.common.NikeActivity
import com.mainway.store.common.convertDpToPixel
import com.mainway.store.data.CartItemCount
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : NikeActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var currentNavController: LiveData<NavController>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */

    @SuppressLint("NewApi")
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationMain)

        val navGraphIds = listOf(R.navigation.home, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount) {
        val badge = bottomNavigationMain.getOrCreateBadge(R.id.cart)
        badge.badgeGravity = BadgeDrawable.BOTTOM_END
        badge.verticalOffset= convertDpToPixel(10f,this).toInt()
        badge.backgroundColor = MaterialColors.getColor(bottomNavigationMain, R.attr.colorPrimary)
        badge.number = cartItemCount.count
        badge.isVisible = cartItemCount.count > 0
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemsCount()

    }

}