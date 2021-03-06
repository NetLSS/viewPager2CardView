package com.lilcode.test.viewpagercardview

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.view_pager2)
        val indicator = findViewById<DotsIndicator>(R.id.dots_indicator)

        var models: MutableList<String> = mutableListOf()

        models.add("AAAAAA")
        models.add("BBBBBB")
        models.add("CCCCCC")
        models.add("DDDDDD")

        var adapter = Adapter(models, this)
        viewPager.adapter = adapter
        viewPager.setPadding(12, 0, 12, 0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })


        val currentVisibleItemPx = dp2px(this, 39f)
        viewPager.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.right = currentVisibleItemPx
                outRect.left = currentVisibleItemPx
            }
        })

        val nextVisibleItemPx = dp2px(this, 27f)
        val pageTranslationX = nextVisibleItemPx + currentVisibleItemPx
        viewPager.offscreenPageLimit = 1

        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))

        transform.addTransformer(ViewPager2.PageTransformer { view: View, position: Float ->
            view.translationX = -pageTranslationX * (position) // ?????? ?????? ?????? ?????? ?????????

            // ??????/?????? ???????????????
            val v = 1 - Math.abs(position)
            view.scaleY = 0.874f + v * 0.126f
        })

        viewPager.setPageTransformer(transform)

        indicator.setViewPager2(viewPager)

    }
}

fun dp2px(ctx: Context, dp: Float): Int {
    val scale: Float = ctx.getResources().getDisplayMetrics().density
    return (dp * scale + 0.5f).toInt()
}