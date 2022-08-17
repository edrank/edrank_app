package com.example.edrank_app.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.databinding.ItemChildrenDetailsBinding
import com.example.edrank_app.models.Children
import com.example.edrank_app.utils.Constants

class ChildrenAdapter() : ListAdapter<Children, ChildrenAdapter.ChildrenViewHolder>(
    ComparatorDiffUtil()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        val binding =
            ItemChildrenDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildrenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    inner class ChildrenViewHolder(private val binding: ItemChildrenDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(response: Children) {
            Log.e(Constants.TAG, response.toString())
            binding.childName.text = response.name
            binding.collegeName.text = response.cid.toString()

        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Children>() {
        override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
            return oldItem == newItem
        }
    }
}