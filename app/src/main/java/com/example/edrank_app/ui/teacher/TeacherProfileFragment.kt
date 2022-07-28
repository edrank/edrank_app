package com.example.edrank_app.ui.teacher

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edrank_app.databinding.FragmentHomeParentBinding
import com.example.edrank_app.databinding.FragmentTeacherProfileBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class TeacherProfileFragment : Fragment() {
    private var _binding: FragmentTeacherProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeacherProfileBinding.inflate(inflater, container, false)


        //code for pie chart
        pieChart = binding.pieChart
        setupPieChart()
        loadPieChartData()

        //password reset ui handling
        binding.resetPassword.setOnClickListener{
            binding.oldPassword.visibility = View.VISIBLE
            binding.newPassword.visibility = View.VISIBLE
            binding.savePassword.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setDrawEntryLabels(false)
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

}