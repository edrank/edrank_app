package com.example.edrank_app.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.R
import com.example.edrank_app.databinding.ItemReviewsBinding
import com.example.edrank_app.models.TeacherFeedback
import com.example.edrank_app.utils.Constants

class TeacherFeedbackAdapter() :
    ListAdapter<TeacherFeedback, TeacherFeedbackAdapter.TeacherFeedbackViewHolder>(
        ComparatorDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherFeedbackViewHolder {
        val binding =
            ItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherFeedbackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherFeedbackViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    inner class TeacherFeedbackViewHolder(private val binding: ItemReviewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(response: TeacherFeedback) {
            Log.e(Constants.TAG, response.toString())
            binding.feedbackText.text = response.text_feedback
            findReviewType(response.sa_score)
        }

        private fun findReviewType(score: String) {
            var dbl = score.toDouble()
            if (dbl >= 66) {
                binding.reviewType.backgroundTintList =
                    ContextCompat.getColorStateList(binding.reviewType.context, R.color.green)
                binding.reviewType.text = "Positive"
            } else if (dbl in 36.0..65.0) {
                binding.reviewType.backgroundTintList =
                    ContextCompat.getColorStateList(binding.reviewType.context, R.color.yellow)
                binding.reviewType.text = "Neutral"
            } else if (dbl in 1.0..35.0) {
                binding.reviewType.backgroundTintList =
                    ContextCompat.getColorStateList(binding.reviewType.context, R.color.red)
                binding.reviewType.text = "Critical"
            }
        }

    }


    class ComparatorDiffUtil : DiffUtil.ItemCallback<TeacherFeedback>() {
        override fun areItemsTheSame(oldItem: TeacherFeedback, newItem: TeacherFeedback): Boolean {
            return oldItem.text_feedback == newItem.text_feedback
        }

        override fun areContentsTheSame(
            oldItem: TeacherFeedback,
            newItem: TeacherFeedback
        ): Boolean {
            return oldItem == newItem
        }
    }
}