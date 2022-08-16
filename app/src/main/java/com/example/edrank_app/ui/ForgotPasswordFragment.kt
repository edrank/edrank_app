package com.example.edrank_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentForgotPasswordBinding
import com.example.edrank_app.models.LoginRequest
import com.example.edrank_app.ui.login.AuthViewModel
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by activityViewModels<AuthViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    lateinit var tenant : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gettingSpinnerData()

        binding.btnLogin.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val loginRequest = getLoginRequest()
                authViewModel.loginUser(tenant, loginRequest)
            } else {
                showValidationErrors(validationResult.second)
            }
        }
        bindObservers()
    }

    private fun gettingSpinnerData() {
        val tenants: Array<String> = arrayOf("STUDENT", "PARENT", "TEACHER", "COLLEGE_ADMIN")
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tenants)
        binding.tenantSpinner.adapter = arrayAdapter
        binding.tenantSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.e("fjks", tenants[p2])
                tokenManager.saveTenant(tenants[p2])
                tenant = tenants[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.e("fkj", "Select something")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun getLoginRequest(): LoginRequest {
        return binding.run {
            LoginRequest(
                txtEmail.text.toString(),
                txtPassword.text.toString()
            )
        }
    }

    private fun showValidationErrors(error: String) {
        binding.txtError.text = String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        return authViewModel.validateCredentials(emailAddress , password, true)
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.data.access_token)
                    when (tenant) {
                        "STUDENT" -> {
                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_homeStudentFragment)

                        }
                        "TEACHER" -> {
                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_homeTeacherFragment)
                        }
                        "PARENT" -> {
                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_homeParentFragment)
                        }
                        else -> {
                            Toast.makeText(context, "Something went wrong with the user type.", Toast
                                .LENGTH_SHORT).show()
                        }
                    }
                }
                is NetworkResult.Error -> {
                    showValidationErrors(it.message.toString())
                }
                is NetworkResult.Loading ->{
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