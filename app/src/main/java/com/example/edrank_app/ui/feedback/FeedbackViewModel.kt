package com.example.edrank_app.ui.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.TeachersForFeedbackRequest
import com.example.edrank_app.models.TeachersForFeedbackResponse
import com.example.edrank_app.repositories.FeedbackRepository
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(private val feedbackRepository: FeedbackRepository) :
    ViewModel() {

    val feedbackData: LiveData<NetworkResult<TeachersForFeedbackResponse>>
        get() = feedbackRepository.feedbackData

    fun getTeachersForFeedback(teachersForFeedbackRequest: TeachersForFeedbackRequest) {
        viewModelScope.launch {
            feedbackRepository.getTeachersForFeedback(teachersForFeedbackRequest)
        }
    }

}