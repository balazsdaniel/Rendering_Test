package com.threenext.renderingtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ViewPager2>(R.id.pager)?.apply {
            offscreenPageLimit = 1
            adapter = PagerAdapter(this@MainActivity)
        }

    }

}