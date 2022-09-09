package com.example.edrank_app.ui.feedback

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentCollegeFeedbackFormBinding
import com.example.edrank_app.models.FeedbackQuestionsRequest
import com.example.edrank_app.models.Teacher
import com.example.edrank_app.models.TeachersForFeedbackRequest
import com.example.edrank_app.ui.adapter.FeedbackQuestionsAdapter
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CollegeFeedbackForm : Fragment() {
    private var _binding: FragmentCollegeFeedbackFormBinding? = null
    private val binding get() = _binding!!

    private val feedbackViewModel by activityViewModels<FeedbackViewModel>()
    private lateinit var questionsAdapter: FeedbackQuestionsAdapter
    private var type: String? = null
    lateinit var teachersNameList: Array<String>


    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollegeFeedbackFormBinding.inflate(inflater, container, false)

        tokenManager = TokenManager(requireContext())
        questionsAdapter = FeedbackQuestionsAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getString("type")
        if (type == "COLLEGE") {
            binding.selectTeacher.isVisible = false
            binding.selectTeacherTv.isVisible = false
            binding.progressBar.isVisible = false
            binding.formTitle.text = "COLLEGE FEEDBACK FORM"

            feedbackViewModel.getQuestions(
                "SC",
                FeedbackQuestionsRequest(tokenManager.getCollegeId()!!.toInt())
            )
        } else if (type == "TEACHER") {
            binding.formTitle.text = "TEACHER FEEDBACK FORM"
            binding.thoughtsTv.text = "Your thoughts about the teacher"

            feedbackViewModel.getTeachers(
                TeachersForFeedbackRequest(
                    tokenManager.getCourseId()!!.toInt()
                )
            )

            feedbackViewModel.getQuestions(
                "ST",
                FeedbackQuestionsRequest(tokenManager.getCollegeId()!!.toInt())
            )
        } else if (type == "PARENT") {
            binding.selectTeacher.isVisible = false
            binding.selectTeacherTv.isVisible = false
            binding.progressBar.isVisible = false
            binding.formTitle.text = "COLLEGE FEEDBACK FORM"

            feedbackViewModel.getQuestions(
                "PC",
                FeedbackQuestionsRequest(tokenManager.getCollegeId()!!.toInt())
            )
        }

        binding.questionsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.questionsRv.adapter = questionsAdapter

        binding.submitBtn.setOnClickListener {
//            submitFeedback()
            binding.progressBar.isVisible = true
            Toast.makeText(
                requireContext(),
                "Grievance uploaded successfully",
                Toast.LENGTH_SHORT
            )
                .show()
            findNavController().navigate(R.id.homeStudentFragment)
            binding.progressBar.isVisible = false

        }
        bindObservers()
    }

    private fun submitFeedback() {

        feedbackViewModel.postFeedbackLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                }
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {
                }
            }
        })

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

        feedbackViewModel.feedbackQuestions.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    questionsAdapter.submitList(it.data?.data?.questions)
                    tokenManager.saveDriveId(it.data?.data?.drive_id!!)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Can't load questions. Error: " + it.data?.error.toString(),
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
//        return teachersList.flatMap { it.map { {it.name} {it.id} } }

    private fun gettingSpinnerData(teacherList: Array<String>) {

        val arrayAdapter =
            ArrayAdapter(
                requireContext(),
                com.example.edrank_app.R.layout.support_simple_spinner_dropdown_item,
                teacherList
            )
        binding.selectTeacher.adapter = arrayAdapter
        binding.selectTeacher.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener,
                AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
//                    tokenManager.saveTeacherId(teacherList[p2].id)
//                    Log.e("fjks", teacherList[p2].name)
//                    Log.e("fjks", teacherList[p2].id.toString())


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Log.e("fkj", "Select something")
                }

                override fun onItemClick(
                    p0: AdapterView<*>?,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    TODO("Not yet implemented")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}