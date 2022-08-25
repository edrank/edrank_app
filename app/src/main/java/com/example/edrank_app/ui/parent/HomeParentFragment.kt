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
import com.example.edrank_app.ui.adapter.ChildrenAdapter
import com.example.edrank_app.ui.feedback.FeedbackViewModel
import com.example.edrank_app.ui.feedback.FeedbackViewModel_Factory
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeParentFragment : Fragment() {
    private var _binding: FragmentHomeParentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ParentViewModel>()
    private val feedbackViewModel by activityViewModels<FeedbackViewModel>()
    private lateinit var childrenAdapter: ChildrenAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeParentBinding.inflate(inflater, container, false)
        childrenAdapter = ChildrenAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getChildrenOfParent()
        binding.childrenRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.childrenRv.adapter = childrenAdapter

        binding.collegeFeedback.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "PARENT")
            findNavController().navigate(R.id.action_homeParentFragment_to_collegeFeedbackForm, bundle)
        }

        bindObservers()
    }

    private fun bindObservers() {
        viewModel.parentData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
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
//                    binding.progressBar.isVisible = true
                }
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}