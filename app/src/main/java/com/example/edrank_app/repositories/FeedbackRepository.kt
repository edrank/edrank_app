package com.example.edrank_app.repositories

import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.FeedbackAPI
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

    lateinit var tokenManager: TokenManager

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

}