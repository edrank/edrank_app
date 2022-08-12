package com.example.edrank_app.ui.teacher

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentHomeTeacherBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.animation.Easing

import com.github.mikephil.charting.formatter.PercentFormatter

import com.github.mikephil.charting.data.PieData

import com.github.mikephil.charting.data.PieDataSet

import com.github.mikephil.charting.utils.ColorTemplate

import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTeacherFragment : Fragment() {
    private var _binding : FragmentHomeTeacherBinding? = null
    private val binding get() = _binding!!
    lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeTeacherBinding.inflate(inflater, container, false)

        //seekbar handling
        binding.seekBar.progress = 87
        binding.seekbarValue.text = binding.seekBar.progress.toString()
        binding.seekBar.isEnabled = false           //to make it non-clickable
//        setSeekbarColor()

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

        //code for pie chart
        pieChart = binding.pieChart
        setupPieChart()
        loadPieChartData()

        return binding.root
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
}