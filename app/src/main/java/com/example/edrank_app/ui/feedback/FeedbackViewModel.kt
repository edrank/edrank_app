package com.example.edrank_app.ui.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.*
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

    val postFeedbackLiveData: LiveData<NetworkResult<PostFeedbackResponse>>
        get() = feedbackRepository.postFeedbackLiveData

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

    fun postFeedback(type : String, postFeedbackRequest: PostFeedbackRequest) {
        viewModelScope.launch {
            feedbackRepository.postFeedback(type, postFeedbackRequest)
        }
    }


}