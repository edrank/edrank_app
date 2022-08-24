package com.example.edrank_app.ui.feedback

import android.R
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
import com.example.edrank_app.databinding.FragmentCollegeFeedbackFormBinding
import com.example.edrank_app.models.Teacher
import com.example.edrank_app.models.TeachersForFeedbackRequest
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager

class collegeFeedbackForm : Fragment() {
    private var _binding: FragmentCollegeFeedbackFormBinding? = null
    private val binding get() = _binding!!

    private val feedbackViewModel by activityViewModels<FeedbackViewModel>()

    lateinit var teachersNameList: Array<String>
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollegeFeedbackFormBinding.inflate(inflater, container, false)

        tokenManager = TokenManager(requireContext())



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedbackViewModel.getTeachers(
            TeachersForFeedbackRequest(
                tokenManager.getCourseId()!!.toInt()
            )
        )
        bindObservers()
    }

    private fun bindObservers() {
        feedbackViewModel.teachersForFeedbackLive.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    teachersNameList = getNames(it.data?.data?.teachers!!)
                    gettingSpinnerData(teachersNameList)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Can't load teachers. Error: " + it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun getNames(teachersList: List<Teacher>): Array<String> {
        return teachersList.map { it.name }.toTypedArray()
    }

    private fun gettingSpinnerData(teacherList: Array<String>) {

        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, teacherList)
        binding.selectTeacher.adapter = arrayAdapter
        binding.selectTeacher.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.e("fjks", teacherList[p2].toString())

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.e("fkj", "Select something")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}