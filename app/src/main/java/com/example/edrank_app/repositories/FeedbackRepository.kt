package com.example.edrank_app.repositories

import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.FeedbacksAPI
import com.example.edrank_app.models.TeachersForFeedbackRequest
import com.example.edrank_app.models.TeachersForFeedbackResponse
import com.example.edrank_app.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class FeedbackRepository  @Inject constructor(private val feedbacksAPI: FeedbacksAPI) {
    private val _feedbackData = MutableLiveData<NetworkResult<TeachersForFeedbackResponse>>()
    val feedbackData get() = _feedbackData

    suspend fun getTeachersForFeedback(teachersForFeedbackRequest: TeachersForFeedbackRequest) {
        _feedbackData.postValue(NetworkResult.Loading())
        val response = feedbacksAPI.getFeedbackTeachers(teachersForFeedbackRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<TeachersForFeedbackResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _feedbackData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _feedbackData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _feedbackData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}