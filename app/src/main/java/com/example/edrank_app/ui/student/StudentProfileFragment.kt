package com.example.edrank_app.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentStudentProfileBinding
import com.example.edrank_app.models.ChangePasswordRequest
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import com.github.mikephil.charting.charts.PieChart

class StudentProfileFragment : Fragment() {
    private var _binding: FragmentStudentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var pieChart: PieChart
    private val viewModel by activityViewModels<StudentProfileViewModel>()
    private val tokenManager: TokenManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.savePassword.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val resetPasswordRequest = getResetPassRequest()
                viewModel.changePassword(resetPasswordRequest)
            } else {
                showValidationErrors(validationResult.second)
            }
        }

        binding.logout.setOnClickListener {
            tokenManager!!.saveToken("")
            findNavController().navigate(R.id.loginFragment)
        }

        bindObservers()

        //password reset ui handling
        binding.resetPassword.setOnClickListener {
            binding.oldPassword.visibility = View.VISIBLE
            binding.newPassword.visibility = View.VISIBLE
            binding.savePassword.visibility = View.VISIBLE
        }
    }

    private fun getResetPassRequest(): ChangePasswordRequest {
        return binding.run {
            ChangePasswordRequest(
                oldPassword.text.toString(),
                newPassword.text.toString()
            )
        }
    }

    private fun showValidationErrors(error: String) {
        binding.txtError.visibility = View.VISIBLE
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val oldPassword = binding.oldPassword.text.toString()
        val newPassword = binding.newPassword.text.toString()

        return viewModel.validateData(oldPassword, newPassword)
    }

    private fun bindObservers() {
        viewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.oldPassword.isVisible = false
                    binding.newPassword.isVisible = false
                    binding.savePassword.isVisible = false
                    Toast.makeText(
                        context,
                        "Your password has been changed successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
                    showValidationErrors(it.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}