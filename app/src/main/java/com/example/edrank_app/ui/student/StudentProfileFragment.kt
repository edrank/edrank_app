package com.example.edrank_app.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edrank_app.databinding.FragmentHomeParentBinding
import com.example.edrank_app.databinding.FragmentStudentProfileBinding

class StudentProfileFragment : Fragment() {
    private var _binding: FragmentStudentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentProfileBinding.inflate(inflater, container, false)



        //password reset ui handling
        binding.resetPassword.setOnClickListener{
            binding.oldPassword.visibility = View.VISIBLE
            binding.newPassword.visibility = View.VISIBLE
            binding.savePassword.visibility = View.VISIBLE
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}