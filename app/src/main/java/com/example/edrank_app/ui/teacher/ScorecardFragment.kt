package com.example.edrank_app.ui.teacher

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.example.edrank_app.databinding.FragmentScorecardBinding
import com.example.edrank_app.models.TopTeachersRequest
import com.example.edrank_app.ui.UserViewModel
import com.example.edrank_app.ui.adapter.TopTeachersAdapter
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import nl.dionsegijn.konfetti.models.Size

class ScorecardFragment : Fragment() {
    private var _binding: FragmentScorecardBinding? = null
    private val binding get() = _binding!!
    private lateinit var teachersAdapter: TopTeachersAdapter
    private val viewModel by activityViewModels<UserViewModel>()
    private val teacherViewModel by activityViewModels<TeacherViewModel>()
    private lateinit var tokenManager: TokenManager
    lateinit var cid: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScorecardBinding.inflate(inflater, container, false)

        tokenManager = TokenManager(requireContext())
        teachersAdapter = TopTeachersAdapter()

        cid = tokenManager.getCollegeId().toString()

        binding.celeBtn.setOnClickListener(View.OnClickListener {

            binding.celebrateView.build()
                .addColors(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN)
                .setDirection(0.0, 359.0)
                .setSpeed(10f, 20f)
                .setFadeOutEnabled(true)
                .setTimeToLive(1000L)
                .addShapes(
                    nl.dionsegijn.konfetti.models.Shape.CIRCLE,
                    nl.dionsegijn.konfetti.models.Shape.RECT
                )
                .addSizes(Size(12, 5F))
                .setPosition(-50f, binding.celebrateView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 2500L)
        })


        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.username.text = tokenManager.getUserName()
        binding.collegeName.text = tokenManager.getCollegeName()
        Log.e("college", tokenManager.getCollegeName().toString())
//
//        teacherViewModel.getTeacherAllRanks("COLLEGE")
//        teacherViewModel.getTeacherAllRanks("NATIONAL")
//        teacherViewModel.getTeacherAllRanks("STATE")

        viewModel.getTopNTeachers(TopTeachersRequest(cid.toInt(), "", "NATIONAL", "", 20))
        binding.topTwentyRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topTwentyRv.adapter = teachersAdapter

        binding.collBtn.setOnClickListener {
            viewModel.getTopNTeachers(TopTeachersRequest(cid.toInt(), "", "COLLEGE", "", 20))
            binding.topTwentyRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.topTwentyRv.adapter = teachersAdapter
        }

        binding.regionalBtn.setOnClickListener {
            viewModel.getTopNTeachers(
                TopTeachersRequest(
                    cid.toInt(),
                    "NEW DELHI",
                    "REGIONAL",
                    "DELHI",
                    20
                )
            )
            binding.topTwentyRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.topTwentyRv.adapter = teachersAdapter
        }

        binding.airBtn.setOnClickListener {
            viewModel.getTopNTeachers(TopTeachersRequest(cid.toInt(), "", "NATIONAL", "", 20))
            binding.topTwentyRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.topTwentyRv.adapter = teachersAdapter
        }

        bindObservers()


    }

    @SuppressLint("SetTextI18n")
    private fun bindObservers() {

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

//        teacherViewModel.teacherMyALlRanksLiveData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
//            when (it) {
//                is NetworkResult.Success -> {
//                    binding.aiRank.text = "AIR : #" + it.data?.data?.rank.toString()
//                    Log.e("score", it.data?.data?.rank.toString())
//                }
//                is NetworkResult.Error -> {
//                    Toast.makeText(
//                        requireContext(),
//                        it.data?.message.toString(),
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//                is NetworkResult.Loading -> {
//                    binding.progressBar.isVisible = true
//                }
//            }
//        })
//
//        teacherViewModel.teacherMyALlRanksLiveData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
//            when (it) {
//                is NetworkResult.Success -> {
//                    binding.stateRank.text = "State : #" + it.data?.data?.rank.toString()
//                    binding.regRank.text = "State : #"  + it.data?.data?.rank.toString()
//                    Log.e("score", it.data?.data?.rank.toString())
//                }
//                is NetworkResult.Error -> {
//                    Toast.makeText(
//                        requireContext(),
//                        it.data?.message.toString(),
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//                is NetworkResult.Loading -> {
//                    binding.progressBar.isVisible = true
//                }
//            }
//        })
//
//
//        teacherViewModel.teacherMyALlRanksLiveData.observe(viewLifecycleOwner, Observer {
//            binding.progressBar.isVisible = false
//            when (it) {
//                is NetworkResult.Success -> {
//                    binding.collegeRank.text = "College : #" + it.data?.data?.rank.toString()
//                    Log.e("score", it.data?.data?.rank.toString())
//
//                }
//                is NetworkResult.Error -> {
//                    Toast.makeText(
//                        requireContext(),
//                        it.data?.message.toString(),
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//                is NetworkResult.Loading -> {
//                    binding.progressBar.isVisible = true
//                }
//            }
//        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}