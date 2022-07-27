package com.example.edrank_app.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentHomeParentBinding
import com.example.edrank_app.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.teacherLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeTeacherFragment)
        }

        binding.studentLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeStudentFragment)
        }

        binding.parentLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeParentFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}