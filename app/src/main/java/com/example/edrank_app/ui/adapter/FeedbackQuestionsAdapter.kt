package com.example.edrank_app.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.edrank_app.R
import com.example.edrank_app.databinding.ItemFeedbackQuestionsBinding
import com.example.edrank_app.databinding.ItemTopCollegesBinding
import com.example.edrank_app.models.FeedbackQuestions
import com.example.edrank_app.models.PostFeedbackRequest
import com.example.edrank_app.models.Question
import com.example.edrank_app.models.TopCollege
import com.example.edrank_app.utils.Constants

class FeedbackQuestionsAdapter() :
    ListAdapter<Question, FeedbackQuestionsAdapter.FeedbackQuestionsViewHolder>(ComparatorDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackQuestionsViewHolder {
        val binding =
            ItemFeedbackQuestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedbackQuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedbackQuestionsViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    inner class FeedbackQuestionsViewHolder(private val binding: ItemFeedbackQuestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(response: Question) {
            Log.e(Constants.TAG, response.toString())
            binding.question.text = response.title
            binding.option1.text = response.option_1
            binding.option2.text = response.option_2
            binding.option3.text = response.option_3
            binding.option4.text = response.option_4
            binding.option5.text = response.option_5
            setOnCheckedChangeListener(response.id)
        }

        private fun setOnCheckedChangeListener(questionId : Int) {
            binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->

                if(R.id.radio_button1 == checkedId){
//                    PostFeedbackRequest(questionId)
                }
                else if(R.id.radio_button2 == checkedId){

                }
                else if(R.id.radio_button3 == checkedId){

                }
                else if(R.id.radio_button4 == checkedId){

                }
                else if(R.id.radio_button5 == checkedId){

                }
                else{
                    Toast.makeText(binding.radioGroup.context, "Please select an option!", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }






    class ComparatorDiffUtil : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }
}