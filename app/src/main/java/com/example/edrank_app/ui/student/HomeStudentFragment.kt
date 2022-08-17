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
    private lateinit var teachersAdapter : TopTeachersAdapter
    private lateinit var collegesAdapter : TopCollegesAdapter
    private lateinit var tokenManager: TokenManager
    private val viewModel by activityViewModels<UserViewModel>()
    lateinit var cid:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeStudentBinding.inflate(inflater, container, false)

        collegesAdapter = TopCollegesAdapter()
        tokenManager = TokenManager(requireContext())
        cid = tokenManager.getCollegeId().toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTopNColleges(TopCollegesRequest("","NATIONAL","",5))
        binding.topCollegeRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topCollegeRv.adapter = collegesAdapter

        binding.studentName.text = tokenManager.getUserName()

        viewModel.getCollegeRank(CollegeRankRequest(cid.toInt(),"","",""))

//        binding.instituteName.text = tokenManager.getCollegeName()

        bindObservers()

        binding.prevFeedback.setOnClickListener {
            findNavController().navigate(R.id.action_homeStudentFragment_to_studentProfileFragment)
        }
        binding.collegeFeedback.setOnClickListener {
            findNavController().navigate(R.id.action_homeStudentFragment_to_collegeFeedbackForm)
        }

        binding.grievanceCellBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeStudentFragment_to_grievanceCell)
        }

        binding.viewMoreTopCollege.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("type", "COLLEGE")
            findNavController().navigate(R.id.action_homeTeacherFragment_to_viewMore, bundle)
        }


    }

    private fun bindObservers() {
        viewModel.collegeRank.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.collegeRankTv.text = it.data?.data?.rank.toString()
                    Log.e("college rank", it.data?.data?.rank.toString() )
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Can't load rank. Error: " + it.data?.message.toString(), Toast.LENGTH_SHORT)
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
                    Toast.makeText(requireContext(), it.data?.message.toString(), Toast.LENGTH_SHORT)
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