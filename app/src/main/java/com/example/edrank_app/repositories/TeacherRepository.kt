package com.example.edrank_app.repositories

import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.TeacherAPI
import com.example.edrank_app.models.TeacherFeedbackResponse
import com.example.edrank_app.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class TeacherRepository @Inject constructor(private val teacherAPI: TeacherAPI) {
    private val _teacherMyFeedbacksLiveData =
        MutableLiveData<NetworkResult<TeacherFeedbackResponse>>()
    val teacherMyFeedbacksLiveData get() = _teacherMyFeedbacksLiveData

    suspend fun teacherMyFeedbacks() {
        _teacherMyFeedbacksLiveData.postValue(NetworkResult.Loading())
        val response = teacherAPI.teacherMyFeedbacks()
        if (response.isSuccessful && response.body() != null) {
            _teacherMyFeedbacksLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _teacherMyFeedbacksLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _teacherMyFeedbacksLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}
