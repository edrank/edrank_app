package com.example.edrank_app.ui.teacher

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.databinding.FragmentReviewsBinding
import com.example.edrank_app.models.TeacherFeedback
import com.example.edrank_app.ui.adapter.TeacherFeedbackAdapter
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : Fragment() {
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!
    private var teacherFeedbackAdapter: TeacherFeedbackAdapter? = null
    private val viewModel by activityViewModels<TeacherViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)


        teacherFeedbackAdapter = TeacherFeedbackAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gettingSpinnerData()

        viewModel.getTeacherFeedbacks()
        binding.studentReviews.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.studentReviews.adapter = teacherFeedbackAdapter
        bindObservers()

    }

    private fun bindObservers() {
        viewModel.teacherMyFeedbacksLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    teacherFeedbackAdapter?.submitList(it.data?.data?.feedbacks)
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

    private fun gettingSpinnerData() {
        val types: Array<String> = arrayOf("Positive", "Neutral", "Critical")
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, types)
        binding.reviewSpinner.adapter = arrayAdapter
        binding.reviewSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                getSelectedCategoryData(p2);
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

//private fun getSelectedCategoryData(categoryID: Int) {
//    //arraylist to hold selected cosmic bodies
//    val cosmicBodies: ArrayList<TeacherFeedback> = ArrayList()
//    if (categoryID == 0) {
//        adapter = ArrayAdapter<Any?>(this, R.layout.simple_list_item_1, getCosmicBodies())
//    } else {
//        //filter by id
//        for (cosmicBody in TeacherFeedback) {
//            if (cosmicBody.getCategoryId() === categoryID) {
//                cosmicBodies.add(cosmicBody)
//            }
//        }
//        //instatiate adapter a
//        adapter = ArrayAdapter<Any?>(this, R.layout.simple_list_item_1, cosmicBodies)
//    }
//    //set the adapter to GridView
//    myListView.setAdapter(adapter)
//}
//
///*
//    when activity is created, setContentView then initializeViews.
//     */
//fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
//    initializeViews()
//}

