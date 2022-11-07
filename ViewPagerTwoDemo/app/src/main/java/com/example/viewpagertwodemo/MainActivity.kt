package com.example.viewpagertwodemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpagertwodemo.databinding.ActivityMainBinding
import com.example.viewpagertwodemo.pageanim.DepthPageTransformer
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(this@MainActivity, "Selected position: ${position}",
                Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var pageNames: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tab nevek inicializálása
        pageNames = resources.getStringArray(R.array.tab_names)

        val fragmentStatePagerAdapter = MyFragmentStatePagerAdapter(this, 2)
        binding.mainViewPager.adapter = fragmentStatePagerAdapter

        // Fölfele-lefele lapozás:
        //binding.mainViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        binding.mainViewPager.registerOnPageChangeCallback(pageChangeCallback)

        // A tabLayout-ot összeköti a ViewPager-el
        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            //To get the first name of doppelganger celebrities
            tab.text = pageNames[position]
        }.attach()

        //binding.mainViewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.mainViewPager.setPageTransformer(DepthPageTransformer())
    }

    override fun onDestroy() {
        binding.mainViewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onDestroy()
    }
}