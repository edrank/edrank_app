package com.example.edrank_app.ui.grievance

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.edrank_app.R
import com.example.edrank_app.databinding.FragmentGrievanceCellBinding
import com.example.edrank_app.models.FileUploadRequest
import com.example.edrank_app.models.GrievanceCellRequest
import com.example.edrank_app.ui.UserViewModel
import com.example.edrank_app.utils.Constants
import com.example.edrank_app.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.properties.Delegates

class grievanceCell : Fragment() {
    private var _binding: FragmentGrievanceCellBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<UserViewModel>()

    private lateinit var requestType: String
    private var filePath: Uri? = null
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrievanceCellBinding.inflate(inflater, container, false)
        requestType = String()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gettingSpinnerData()
        bindObservers()
        var reportToCc : Boolean = true
        binding.yes.setOnClickListener {
           reportToCc = true
            Toast.makeText(
                requireContext(),
                "Yes",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        binding.no.setOnClickListener {
            reportToCc = false
            Toast.makeText(
                requireContext(),
                "No",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        binding.submitBtn.setOnClickListener {
            viewModel.submitGrievance(
                GrievanceCellRequest(
                    binding.ccResponse.text.toString(),
                    "college",
                    binding.description.text.toString(),
                    reportToCc,
                    "",
                    binding.subject.text.toString()
                )
            )
        }
    }

    private fun gettingSpinnerData() {
        val tenants: Array<String> = arrayOf("college", "teacher", "others")
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tenants)
        binding.tenantSpinner.adapter = arrayAdapter
        binding.tenantSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.e("fjks", tenants[p2])
                requestType = tenants[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.e("fkj", "Select something")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun getGrievanceRequest(): GrievanceCellRequest {
        return binding.run {
            GrievanceCellRequest(
                binding.ccResponse.text.toString(),
                requestType,
                binding.description.text.toString(),
                true,
                "",
                binding.subject.text.toString(),
            )
        }
    }


    private fun bindObservers() {
        viewModel.fileUpload.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
//                    fileId = it.data?.data?.file_registry_id!!
                    Log.e("response", it.toString())
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Can't upload. Error: " + it.data?.error,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })

        viewModel.grievanceLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Log.e("grievance", it.toString())
                    Toast.makeText(
                        requireContext(),
                        "Grivance uploaded" + it.data?.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Grievance uploaded successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    findNavController().navigate(R.id.homeStudentFragment)
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

