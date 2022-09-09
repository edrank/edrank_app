package com.example.edrank_app.ui.login

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentLoginBinding
import com.example.edrank_app.models.LoginRequest
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by activityViewModels<AuthViewModel>()
    var languageToLoad = "en"

    @Inject
    lateinit var tokenManager: TokenManager
    lateinit var tenant: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        tenant = ""
        tokenManager = TokenManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gettingSpinnerData()
        gettingLangSpinnerData()

        binding.btnLogin.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val loginRequest = getLoginRequest()
                authViewModel.loginUser(tenant, loginRequest)
            } else {
                showValidationErrors(validationResult.second)
            }
        }

        binding.credentials.setOnClickListener {
            findNavController().navigate(com.example.edrank_app.R.id.action_loginFragment_to_credentialsFragment)
        }


        bindObservers()
    }

    private fun gettingSpinnerData() {
        val tenants: Array<String> = arrayOf("STUDENT", "PARENT", "TEACHER")
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

    private fun gettingLangSpinnerData() {
        val tenants: Array<String> = arrayOf("English", "हिंदी")
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tenants)
        binding.languageSpinner.adapter = arrayAdapter
        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener,
                AdapterView.OnItemClickListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (tenants[p2] == "हिंदी") {
                        val locale = Locale("hi")
                        Locale.setDefault(locale)
                        val config = Configuration()
                        config.locale = locale
                        context?.getResources()
                            ?.updateConfiguration(
                                config,
                                requireContext().getResources().getDisplayMetrics()
                            )
                    } else if (tenants[p2]=="English") {
                        val locale = Locale("en")
                        Locale.setDefault(locale)
                        val config = Configuration()
                        config.locale = locale
                        context?.getResources()
                            ?.updateConfiguration(
                                config,
                                requireContext().getResources().getDisplayMetrics()
                            )
                    }


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
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        return authViewModel.validateCredentials(emailAddress, password, true)
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.data.access_token)

                    when (tenant) {
                        "STUDENT" -> {
                            findNavController().navigate(R.id.action_loginFragment_to_homeStudentFragment)
                            tokenManager.saveCollegeId(it.data!!.data.user.cid)
                            tokenManager.saveUserName(it.data!!.data.user.name)
                            tokenManager.saveCourseId(it.data!!.data.user.course_id.toString())

                        }
                        "TEACHER" -> {
                            findNavController().navigate(R.id.action_loginFragment_to_homeTeacherFragment)
                            tokenManager.saveCollegeId(it.data!!.data.user.cid)
                            tokenManager.saveUserName(it.data!!.data.user.name)
                            tokenManager.saveCourseId(it.data!!.data.user.course_id.toString())

                        }
                        "PARENT" -> {
                            findNavController().navigate(R.id.action_loginFragment_to_homeParentFragment)
                        }
                        else -> {
                            Log.e("Login", "Something went wrong with the user type.")
                        }
                    }
                }
                is NetworkResult.Error -> {
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