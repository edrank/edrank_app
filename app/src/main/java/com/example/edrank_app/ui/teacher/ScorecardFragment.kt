package com.example.edrank_app.ui.teacher

import android.graphics.Color
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edrank_app.databinding.FragmentHomeParentBinding
import com.example.edrank_app.databinding.FragmentScorecardBinding
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Size

class ScorecardFragment : Fragment() {
    private var _binding: FragmentScorecardBinding? = null
    private val binding get() = _binding!!

//    private lateinit var cel: KonfettiView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScorecardBinding.inflate(inflater, container, false)

        binding.celeBtn .setOnClickListener(View.OnClickListener {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}