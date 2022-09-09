 package com.example.edrank_app.ui.parent

import android.os.Bundle
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
import com.example.edrank_app.databinding.FragmentHomeParentBinding
import com.example.edrank_app.models.TopCollegesRequest
import com.example.edrank_app.models.TopTeachersRequest
import com.example.edrank_app.ui.UserViewModel
import com.example.edrank_app.ui.adapter.ChildrenAdapter
import com.example.edrank_app.ui.adapter.TopCollegesAdapter
import com.example.edrank_app.ui.adapter.TopTeachersAdapter
import com.example.edrank_app.ui.feedback.FeedbackViewModel
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeParentFragment : Fragment() {
    private var _binding: FragmentHomeParentBinding? = null
    private val binding get() = _binding!!
    lateinit var tokenManager: TokenManager
    private lateinit var collegesAdapter: TopCollegesAdapter
    private lateinit var teachersAdapter: TopTeachersAdapter
    private val viewModel by activityViewModels<ParentViewModel>()
    private val topCollegesModel by activityViewModels<UserViewModel>()
    private val feedbackViewModel by activityViewModels<FeedbackViewModel>()
    private lateinit var childrenAdapter: ChildrenAdapter
    lateinit var cid: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeParentBinding.inflate(inflater, container, false)

        collegesAdapter = TopCollegesAdapter()
        childrenAdapter = ChildrenAdapter(::onChildClicked)
        teachersAdapter = TopTeachersAdapter()
        tokenManager = TokenManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cid = tokenManager.getCollegeId().toString()

        binding.studentName.text = tokenManager.getUserName()

        viewModel.getChildrenOfParent()
        binding.childrenRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.childrenRv.adapter = childrenAdapter

        topCollegesModel.getTopNColleges(TopCollegesRequest("", "NATIONAL", "", 5))
        binding.topCollegeParentRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topCollegeParentRv.adapter = collegesAdapter

        topCollegesModel.getTopNTeachers(TopTeachersRequest(cid.toInt(), "","NATIONAL", "", 5))
        binding.topTeacherRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topTeacherRv.adapter = teachersAdapter

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

        binding.linearLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeParentFragment_to_collegeFeedbackForm)
        }

        binding.myProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "PARENT")
            findNavController().navigate(R.id.studentProfileFragment, bundle)

        }
        bindObservers()
    }

    private fun bindObservers() {
        viewModel.parentData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    childrenAdapter.submitList(it.data?.data?.students)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Can't load children. Error: " + it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })

        topCollegesModel.topNTeachers.observe(viewLifecycleOwner, Observer {
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

        topCollegesModel.topNColleges.observe(viewLifecycleOwner, Observer {
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

    }

    private fun onChildClicked(){

        val bundle =  Bundle()
        bundle.putString("type", "PARENT")
        findNavController().navigate(R.id.action_homeParentFragment_to_collegeFeedbackForm, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}