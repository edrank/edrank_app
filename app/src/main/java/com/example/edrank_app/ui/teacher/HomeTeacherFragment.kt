package com.example.edrank_app.ui.teacher

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentHomeTeacherBinding
import com.example.edrank_app.models.CollegeRankRequest
import com.example.edrank_app.models.TopCollegesRequest
import com.example.edrank_app.models.TopTeachersRequest
import com.example.edrank_app.ui.UserViewModel
import com.example.edrank_app.ui.adapter.TopCollegesAdapter
import com.example.edrank_app.ui.adapter.TopTeachersAdapter
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTeacherFragment : Fragment() {
    private var _binding: FragmentHomeTeacherBinding? = null
    private val binding get() = _binding!!
    lateinit var pieChart: PieChart
    private lateinit var teachersAdapter : TopTeachersAdapter
    private lateinit var collegesAdapter : TopCollegesAdapter
    private val viewModel by activityViewModels<UserViewModel>()
    private lateinit var tokenManager: TokenManager
    lateinit var cid:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeTeacherBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())

        cid = tokenManager.getCollegeId().toString()
        Log.e("jfjshigjdklrj", cid)

        collegesAdapter = TopCollegesAdapter()
        teachersAdapter = TopTeachersAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        topTeachersRequest.cid = tokenManager!!.getCid()!!

        viewModel.getTopNTeachers(TopTeachersRequest(cid.toInt(),"","COLLEGE","",3))
        binding.topTeacherRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topTeacherRv.adapter = teachersAdapter

        viewModel.getTopNColleges(TopCollegesRequest("","NATIONAL","",5))
        binding.topCollegeRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topCollegeRv.adapter = collegesAdapter

        viewModel.getCollegeRank(CollegeRankRequest(cid.toInt(),"","",""))

        bindObservers()

        handleUi()

        //code for pie chart
        pieChart = binding.pieChart
        setupPieChart()
        loadPieChartData()
    }

    private fun handleUi() {

        //seekbar handling
        binding.seekBar.progress = 87
        binding.seekbarValue.text = binding.seekBar.progress.toString()
        binding.seekBar.isEnabled = false           //to make it non-clickable

        //for 3 button menu
        binding.reviews.setOnClickListener {
            findNavController().navigate(R.id.action_homeTeacherFragment_to_teacherReviewsFragment)
        }

        binding.scorecard.setOnClickListener {
            findNavController().navigate(R.id.action_homeTeacherFragment_to_scorecardFragment)
        }

        binding.otherFeature.setOnClickListener {
            findNavController().navigate(R.id.action_homeTeacherFragment_to_teacherProfileFragment)
            //            Toast.makeText(context,"This feature will be available in the next version",Toast.LENGTH_LONG).show()
        }
        binding.viewMoreTopTeachers.setOnClickListener {
            findNavController().navigate(R.id.action_homeTeacherFragment_to_viewMore)
        }

        binding.viewMoreTopCollege.setOnClickListener {
            findNavController().navigate(R.id.action_homeTeacherFragment_to_viewMore)
        }
    }

    private fun bindObservers() {
        viewModel.collegeRank.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.collegeRank.text = it.data?.data?.rank.toString()
                    Log.e("rank", it.data?.data?.rank.toString())
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

        viewModel.topNTeachers.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    teachersAdapter.submitList(it.data?.data?.teachers)
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


    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setDrawEntryLabels(false)
//        pieChart.setEntryLabelTextSize(8F)
//        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Your Reviews"
        pieChart.setCenterTextSize(12F)
        pieChart.description.isEnabled = false
        val l: Legend = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadPieChartData() {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(0.7f, "Positive"))
        entries.add(PieEntry(0.2f, "Neutral"))
        entries.add(PieEntry(0.1f, "Negative"))

        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
//    private fun setSeekbarColor() {
//        if (binding.seekBar.progress >= 65){
//            binding.seekBar.progressDrawable.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green), PorterDuff.Mode.SRC_ATOP)
//        }
//        else if (binding.seekBar.progress in 35..64){
//            binding.seekBar.progressDrawable.setColorFilter(ContextCompat.getColor(requireContext(), R.color.yellow), PorterDuff.Mode.SRC_ATOP)
//        }
//        else{
//            binding.seekBar.progressDrawable.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red), PorterDuff.Mode.SRC_ATOP)
//
//        }
//    }
