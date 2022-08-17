package com.example.edrank_app.ui.teacher

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentTeacherReviewsBinding
import com.example.edrank_app.ui.adapter.ViewPageAdapter
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class TeacherReviewsFragment : Fragment() {
    private var _binding: FragmentTeacherReviewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeacherReviewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager(binding.viewPager)

        binding.bottomNav.setItemSelected(
            R.id.reviews,
            true
        );
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("ResourceAsColor")
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.bottomNav.setItemSelected(R.id.reviews, true)
                    }
                    1 -> {
                        binding.bottomNav.setItemSelected(R.id.questions, true)
                    }
                }
                super.onPageSelected(position)
            }
        })

        binding.bottomNav.setOnItemSelectedListener(object :
            ChipNavigationBar.OnItemSelectedListener {
            @SuppressLint("ResourceAsColor")
            override fun onItemSelected(id: Int) {
                when (id) {
                    R.id.reviews -> {
                        binding.viewPager.currentItem = 0
                    }
                    R.id.questions -> {
                        binding.viewPager.currentItem = 1
                    }
                }
            }
        })
    }

    private fun setupViewPager(viewPager: ViewPager2?) {
        val exViewPageAdapter = ViewPageAdapter(this)
        ViewPageAdapter.addFragment(ReviewsFragment(), "Reviews")
        ViewPageAdapter.addFragment(QuestionsFragment(), "Questions")
        viewPager!!.adapter = exViewPageAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}