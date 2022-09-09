package com.example.edrank_app.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.edrank_app.databinding.FragmentCredentialsBinding
import com.example.edrank_app.databinding.FragmentViewMoreBinding
import com.example.edrank_app.ui.adapter.TopCollegesAdapter
import com.example.edrank_app.ui.adapter.TopTeachersAdapter
import com.example.edrank_app.utils.TokenManager

class CredentialsFragment : Fragment() {

    private var _binding: FragmentCredentialsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCredentialsBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentEmail.setOnClickListener {
            copyTextToClipboard("test+1@gmail.com")
        }

        binding.parentEmail.setOnClickListener {
            copyTextToClipboard("adam@gmail.com")
        }

        binding.teacherEmail.setOnClickListener {
            copyTextToClipboard("omgupta1608+3@gmail.com")
        }

        binding.studentPassword.setOnClickListener {
            copyTextToClipboard("test123")
        }

        binding.teacherPassword.setOnClickListener {
            copyTextToClipboard("rishi123")
        }

        binding.parentPassword.setOnClickListener {
            copyTextToClipboard("adam123")
        }

    }

    private fun copyTextToClipboard(textToCopy: String) {

        var myClipboard = getSystemService(requireContext(), ClipboardManager::class.java) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", textToCopy)

        myClipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}