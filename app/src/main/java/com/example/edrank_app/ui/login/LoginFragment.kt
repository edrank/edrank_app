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
import android.content.Intent
import com.example.edrank_app.ui.ForgotPasswordFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.teacherLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeTeacherFragment)
        }

        binding.studentLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeStudentFragment)
        }

        binding.parentLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeParentFragment)
        }

        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}