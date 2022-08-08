package com.example.edrank_app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.edrank_app.ui.teacher.QuestionsFragment
import com.example.edrank_app.ui.teacher.ReviewsFragment

class ViewPageAdapter(fa: Fragment) : FragmentStateAdapter(fa) {

    companion object {
        private val fragmentList: ArrayList<Fragment> = ArrayList()
        private val fragmentTitle: ArrayList<String> = ArrayList()

        @JvmStatic
        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitle.add(title)
        }

        private const val NUM_PAGES = 3
    }

    init {
        fragmentList.clear()
        fragmentTitle.clear()
    }

    override fun getItemCount(): Int = Companion.NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ReviewsFragment()
            }
            1 -> {
                QuestionsFragment()
            }
            else -> ReviewsFragment()
        }
    }
}