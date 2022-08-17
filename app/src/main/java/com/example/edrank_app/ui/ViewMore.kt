package com.example.edrank_app

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.databinding.FragmentViewMoreBinding
import com.example.edrank_app.models.TopCollegesRequest
import com.example.edrank_app.models.TopTeachersRequest
import com.example.edrank_app.ui.UserViewModel
import com.example.edrank_app.ui.adapter.TopCollegesAdapter
import com.example.edrank_app.ui.adapter.TopTeachersAdapter
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager

class ViewMore : Fragment() {

    private var _binding: FragmentViewMoreBinding? = null
    private val binding get() = _binding!!
    private var type: String? = null
    private lateinit var teachersAdapter: TopTeachersAdapter
    private lateinit var collegesAdapter: TopCollegesAdapter
    private val viewModel by activityViewModels<UserViewModel>()

    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewMoreBinding.inflate(inflater, container, false)
        type = arguments?.getString("type")

        collegesAdapter = TopCollegesAdapter()
        teachersAdapter = TopTeachersAdapter()

        tokenManager = TokenManager(requireContext())



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var cid = tokenManager.getCollegeId()
        Log.e("bfehsjgjhvbjhgeb", type!!)

        if(type == "COLLEGE"){
            viewModel.getTopNColleges(TopCollegesRequest("", "NATIONAL", "", -1))
            binding.viewMoreCollegeRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.viewMoreCollegeRv.adapter = collegesAdapter
        }
        else if(type == "TEACHER"){
            viewModel.getTopNTeachers(TopTeachersRequest(cid!!.toInt(), "", "COLLEGE", "", 3))
            binding.viewMoreTeacherRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.viewMoreTeacherRv.adapter = teachersAdapter
        }

        else{
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }

        bindObservers()

    }

    private fun bindObservers() {

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
                    teachersAdapter.submitList((it.data?.data?.teachers))
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
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