package com.example.edrank_app.repositories

import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.FeedbackAPI
import com.example.edrank_app.models.FeedbackQuestionsRequest
import com.example.edrank_app.models.FeedbackQuestionsResponse
import com.example.edrank_app.models.TeachersForFeedbackRequest
import com.example.edrank_app.models.TeachersForFeedbackResponse
import com.example.edrank_app.utils.NetworkResult
import com.example.edrank_app.utils.TokenManager
import org.json.JSONObject
import javax.inject.Inject

class FeedbackRepository @Inject constructor(private val feedbackAPI: FeedbackAPI) {
    private val _getTeachersForFeedback =
        MutableLiveData<NetworkResult<TeachersForFeedbackResponse>>()
    val getTeachersForFeedback get() = _getTeachersForFeedback

    private val _getFeedbackQuestionsLiveData =
        MutableLiveData<NetworkResult<FeedbackQuestionsResponse>>()
    val getFeedbackQuestionsLiveData get() = _getFeedbackQuestionsLiveData

    suspend fun getTeachersForFeedback(teachersForFeedbackRequest: TeachersForFeedbackRequest) {
        _getTeachersForFeedback.postValue(NetworkResult.Loading())
        val response = feedbackAPI.getTeachersForFeedback(teachersForFeedbackRequest)
        if (response.isSuccessful && response.body() != null) {
            _getTeachersForFeedback.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _getTeachersForFeedback.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _getTeachersForFeedback.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun getFeedbackQuestions(type: String, feedbackQuestionsRequest: FeedbackQuestionsRequest) {
        _getFeedbackQuestionsLiveData.postValue(NetworkResult.Loading())
        val response = feedbackAPI.getFeedbackQuestions(type, feedbackQuestionsRequest)
        if (response.isSuccessful && response.body() != null) {
            _getFeedbackQuestionsLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _getFeedbackQuestionsLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _getFeedbackQuestionsLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }



}