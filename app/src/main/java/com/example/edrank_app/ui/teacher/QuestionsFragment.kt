package com.example.edrank_app.ui.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edrank_app.databinding.FragmentQuestionsBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class QuestionsFragment : Fragment() {
    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    lateinit var chart : BarChart


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)

        var barDataSet : BarDataSet = BarDataSet(dataValues(),"Dataset")
        var barData : BarData = BarData()
        barData.addDataSet(barDataSet)
        chart.setData(barData)
        chart.invalidate()

        return binding.root
    }

    fun dataValues () : ArrayList<BarEntry>{
        val dataVals : ArrayList<BarEntry> = ArrayList()

//        dataVals.add(BarEntry(0.0,3.0))

        return dataVals
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}