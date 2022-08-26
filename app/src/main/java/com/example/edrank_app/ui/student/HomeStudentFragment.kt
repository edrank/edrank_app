package com.example.edrank_app.ui.student

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentHomeStudentBinding
import com.example.edrank_app.models.CollegeRankRequest
import com.example.edrank_app.models.TopCollegesRequest
import com.example.edrank_app.models.TopTeachersRequest
import com.example.edrank_app.ui.UserViewModel
import com.example.edrank_app.ui.adapter.TopCollegesAdapter
import com.example.edrank_app.ui.adapter.TopTeachersAdapter
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeStudentFragment : Fragment() {
    private var _binding: FragmentHomeStudentBinding? = null
    private val binding get() = _binding!!
    private lateinit var teachersAdapter: TopTeachersAdapter
    private lateinit var collegesAdapter: TopCollegesAdapter
    private lateinit var tokenManager: TokenManager
    private val viewModel by activityViewModels<UserViewModel>()
    lateinit var cid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeStudentBinding.inflate(inflater, container, false)

        collegesAdapter = TopCollegesAdapter()
        teachersAdapter = TopTeachersAdapter()
        tokenManager = TokenManager(requireContext())
        cid = tokenManager.getCollegeId().toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCollegeDetails()

        viewModel.getTopNColleges(TopCollegesRequest("", "NATIONAL", "", 5))
        binding.topCollegeRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topCollegeRv.adapter = collegesAdapter

        viewModel.getTopNTeachers(TopTeachersRequest(cid.toInt(), "","NATIONAL", "", 5))
        binding.topTeacherRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topTeacherRv.adapter = teachersAdapter

        binding.studentName.text = tokenManager.getUserName()

        viewModel.getCollegeRank(CollegeRankRequest(cid.toInt(), "", "NATIONAL", ""))

        bindObservers()

        handleUi()

    }

    private fun handleUi() {

        binding.teacherFeedback.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "TEACHER")
            findNavController().navigate(R.id.action_homeStudentFragment_to_collegeFeedbackForm, bundle)
        }

        binding.collegeFeedback.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "COLLEGE")
            findNavController().navigate(R.id.action_homeStudentFragment_to_collegeFeedbackForm, bundle)
        }

        binding.myProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "STUDENT")
            findNavController().navigate(R.id.action_homeStudentFragment_to_studentProfileFragment, bundle)
        }

        binding.grievanceCellBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeStudentFragment_to_grievanceCell)
        }


        binding.viewMoreTopCollege.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "COLLEGE")
            findNavController().navigate(R.id.viewMore, bundle)
        }


        binding.viewMoreTopTeachers.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "TEACHER")
            findNavController().navigate(R.id.viewMore, bundle)
        }

    }

    private fun bindObservers() {
        viewModel.collegeLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.instituteName.text = it.data?.data?.college?.name
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Can't load college. Error: " + it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })

        viewModel.collegeRank.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.collegeRankTv.text = it.data?.data?.rank.toString()
                    Log.e("college rank", it.data?.data?.rank.toString())
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Can't load rank. Error: " + it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })

        viewModel.topNColleges.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    collegesAdapter.submitList(it.data?.data?.colleges)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })

        viewModel.topNTeachers.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    teachersAdapter.submitList(it.data?.data?.teachers)

                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
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