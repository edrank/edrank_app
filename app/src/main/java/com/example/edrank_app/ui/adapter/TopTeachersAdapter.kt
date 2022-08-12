package com.example.edrank_app.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.databinding.Top3TeacherItemsBinding
import com.example.edrank_app.models.Teacher
import com.example.edrank_app.utils.Constants.TAG

class TopTeachersAdapter() :
    ListAdapter<Teacher, TopTeachersAdapter.TopTeacherViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTeacherViewHolder {
        val binding =
            Top3TeacherItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopTeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopTeacherViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class TopTeacherViewHolder(private val binding: Top3TeacherItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(response: Teacher) {
            Log.e(TAG, response.toString())
            binding.teacherName.text = response.name
            binding.rank.text = response.rank.toString()
//            response.score
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Teacher>() {
        override fun areItemsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
            return oldItem == newItem
        }
    }
}