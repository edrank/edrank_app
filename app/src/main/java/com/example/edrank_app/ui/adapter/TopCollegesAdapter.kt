package com.example.edrank_app.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.databinding.ItemTopCollegesBinding
import com.example.edrank_app.models.TopCollege
import com.example.edrank_app.utils.Constants

class TopCollegesAdapter() :
    ListAdapter<TopCollege, TopCollegesAdapter.TopCollegesViewHolder>(ComparatorDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCollegesViewHolder {
        val binding =
            ItemTopCollegesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopCollegesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopCollegesViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    inner class TopCollegesViewHolder(private val binding: ItemTopCollegesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(response: TopCollege) {
            Log.e(Constants.TAG, response.toString())
            binding.rank.text = response.rank.toString()
            binding.collegeName.text = response.name.toString()
            binding.feedbackScore.text = response.score.toString() + " points"
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<TopCollege>() {
        override fun areItemsTheSame(oldItem: TopCollege, newItem: TopCollege): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TopCollege, newItem: TopCollege): Boolean {
            return oldItem == newItem
        }
    }
}