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
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentTeacherProfileBinding
import com.example.edrank_app.models.ChangePasswordRequest
import com.example.edrank_app.models.TeacherProfileResponse
import com.example.edrank_app.ui.UserViewModel
import com.example.edrank_app.utils.Constants.TAG
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
import javax.inject.Inject

@AndroidEntryPoint
class TeacherProfileFragment : Fragment() {
    private var _binding: FragmentTeacherProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var pieChart: PieChart
    private val viewModel by activityViewModels<UserViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    private var profileData: TeacherProfileResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeacherProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialData()

        binding.savePassword.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val resetPasswordRequest = getResetPassRequest()
                viewModel.changePassword(resetPasswordRequest)
            } else {
                showValidationErrors(validationResult.second)
            }
        }

        binding.logout.setOnClickListener {
            tokenManager!!.saveToken("")
            findNavController().navigate(R.id.loginFragment)
        }

        bindObservers()

        //code for pie chart
        pieChart = binding.pieChart
        setupPieChart()
        loadPieChartData()

        //password reset ui handling
        binding.resetPassword.setOnClickListener {
            binding.oldPassword.visibility = View.VISIBLE
            binding.newPassword.visibility = View.VISIBLE
            binding.savePassword.visibility = View.VISIBLE
        }
    }

    private fun getResetPassRequest(): ChangePasswordRequest {
        return binding.run {
            ChangePasswordRequest(
                oldPassword.text.toString(),
                newPassword.text.toString()
            )
        }
    }

    private fun showValidationErrors(error: String) {
        binding.txtError.visibility = View.VISIBLE
        binding.txtError.text =
            String.format(resources.getString(R.string.txt_error_message, error))
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val oldPassword = binding.oldPassword.text.toString()
        val newPassword = binding.newPassword.text.toString()

        return viewModel.validateData(oldPassword, newPassword)
    }

    private fun setInitialData() {
        viewModel.getTeacherProfile()
        binding.progressBar.isVisible = true
        loadProfile()
    }

    private fun loadProfile() {
        viewModel.teacherMyProfile.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Log.e(TAG, it.data?.data?.profile?.cid.toString())
                    binding.teacherName.text = it.data?.data?.profile?.name
                    binding.department.text = it.data?.data?.profile?.department
                    binding.designation.text = it.data?.data?.profile?.designation
//                    binding.score.text = it.data?.data?.profile?.score
                    val cid = it.data?.data?.profile?.cid.toString()
                    tokenManager.saveCid(cid)
                }
                is NetworkResult.Error -> {
                    showValidationErrors(it.message.toString())
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun bindObservers() {
        viewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    binding.oldPassword.isVisible = false
                    binding.newPassword.isVisible = false
                    binding.savePassword.isVisible = false
                    Toast.makeText(
                        context,
                        "Your password has been changed successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Error -> {
                    showValidationErrors(it.message.toString())
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
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