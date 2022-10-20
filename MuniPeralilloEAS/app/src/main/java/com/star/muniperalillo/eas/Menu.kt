package com.star.muniperalillo.eas

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import es.dmoral.toasty.Toasty
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar

class Menu : FragmentActivity() {
    val NUM_PAGES = 2
    var mPager: ViewPager? = null
    var mPagerAdapter: PagerAdapter? = null
    var page_listener: PageListener? = null
    var currentPage = 0
    var f: Fragment? = null
    var menu_abajo: ExpandableBottomBar? = null
    var REQUEST_ACCESS_FINE = 0

    override fun onResume() {
        super.onResume()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
    }

    override fun onRestart() {
        super.onRestart()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        if(Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor = resources.getColor(R.color.white)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        delayedHide(300)
        mHideHandler.removeMessages(0)
        hideSystemUI()
        mPager = findViewById(R.id.view_pager)
        menu_abajo = findViewById(R.id.menu_abajo)
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager!!.setAdapter(mPagerAdapter)
        page_listener = PageListener()
        mPager!!.setOnPageChangeListener(page_listener)
        mPager!!.offscreenPageLimit = 0
        // menu_abajo.on(R.id.Noticias, true)
        menu_abajo!!.onItemSelectedListener = {view, item ->
            when(item.itemId){
                2131230726 -> mPager!!.currentItem = 0
                2131230727-> mPager!!.currentItem = 1
            }
        }
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                delayedHide(300)
                mHideHandler.removeMessages(0)
                hideSystemUI()
            } else {
                delayedHide(300)
                mHideHandler.removeMessages(0)
                hideSystemUI()
            }
        }
        pedir_gps()
    }

    override fun onBackPressed() {
        when {
            mPager!!.currentItem == 0 -> {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            else -> {
                mPager!!.currentItem = mPager!!.currentItem - 1
            }
        }
    }

    inner class ScreenSlidePagerAdapter(fm: FragmentManager?) :
        FragmentStatePagerAdapter(fm!!) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> f = AyudaFragment()
                1 -> f = CuentaFragment()
            }
            return f!!
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }

        override fun getCount(): Int {
            return NUM_PAGES
        }
    }

    inner class PageListener : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            currentPage = position
            when (currentPage){
                0 -> menu_abajo!!.select(2131230726)
                1 -> menu_abajo!!.select(2131230727)
            }
            //ctv_Number.setText("Page :" + currentPage);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ACCESS_FINE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                //pedir_gps()
            }
        }
    }

    fun pedir_gps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action. When the window gains focus,
        // hide the system UI.
        when{
            hasFocus -> delayedHide(300)
            else -> mHideHandler.removeMessages(0)
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private val mHideHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            hideSystemUI()
        }
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeMessages(0)
        mHideHandler.sendEmptyMessageDelayed(0, delayMillis.toLong())
    }
}
