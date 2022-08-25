package com.example.edrank_app.ui.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.FeedbackQuestionsRequest
import com.example.edrank_app.models.FeedbackQuestionsResponse
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

    val teachersForFeedbackLive: LiveData<NetworkResult<TeachersForFeedbackResponse>>
        get() = feedbackRepository.getTeachersForFeedback


    val feedbackQuestions: LiveData<NetworkResult<FeedbackQuestionsResponse>>
        get() = feedbackRepository.getFeedbackQuestionsLiveData

    fun getTeachers(teachersForFeedbackRequest: TeachersForFeedbackRequest) {
        viewModelScope.launch {
            feedbackRepository.getTeachersForFeedback(teachersForFeedbackRequest)
        }
    }

    fun getQuestions(type : String, feedbackQuestionsRequest: FeedbackQuestionsRequest) {
        viewModelScope.launch {
            feedbackRepository.getFeedbackQuestions(type, feedbackQuestionsRequest)
        }
    }

}