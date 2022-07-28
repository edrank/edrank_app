package com.example.edrank_app.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentHomeStudentBinding

class HomeStudentFragment : Fragment() {
    private var _binding: FragmentHomeStudentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeStudentBinding.inflate(inflater, container, false)

        binding.prevFeedback.setOnClickListener {
            findNavController().navigate(R.id.action_homeStudentFragment_to_studentProfileFragment)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}