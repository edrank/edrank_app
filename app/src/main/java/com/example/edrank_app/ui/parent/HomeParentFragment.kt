package com.example.edrank_app.ui.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.databinding.FragmentHomeParentBinding
import com.example.edrank_app.ui.adapter.ChildrenAdapter
import com.example.edrank_app.utils.NetworkResult

class HomeParentFragment : Fragment() {
    private var _binding: FragmentHomeParentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ParentViewModel>()
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

        binding.topCollegeParentsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.topCollegeParentsRv.adapter = childrenAdapter

        viewModel.getChildrenOfParent()

        bindObservers()
    }

    private fun bindObservers() {
        viewModel.parentData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    childrenAdapter.submitList(it.data?.data?.children)
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