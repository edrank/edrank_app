package com.example.edrank_app.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.edrank_app.databinding.FragmentCollegeFeedbackFormBinding

class collegeFeedbackForm : Fragment() {
    private var _binding: FragmentCollegeFeedbackFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollegeFeedbackFormBinding.inflate(inflater, container, false)




        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}