package com.example.edrank_app.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.TeacherAPI
import com.example.edrank_app.models.GraphSARequest
import com.example.edrank_app.models.GraphSAResponse
import com.example.edrank_app.models.TeacherAllRanksResponse
import com.example.edrank_app.models.TeacherFeedbackResponse
import com.example.edrank_app.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class TeacherRepository @Inject constructor(private val teacherAPI: TeacherAPI) {
    private val _teacherMyFeedbacksLiveData =
        MutableLiveData<NetworkResult<TeacherFeedbackResponse>>()
    val teacherMyFeedbacksLiveData get() = _teacherMyFeedbacksLiveData

    private val _teacherMyALlRanksLiveData =
        MutableLiveData<NetworkResult<TeacherAllRanksResponse>>()
    val teacherMyALlRanksLiveData get() = _teacherMyALlRanksLiveData

    private val _saGraphLiveData = MutableLiveData<NetworkResult<GraphSAResponse>>()
    val saGraphLiveData get() = _saGraphLiveData

    suspend fun teacherMyFeedbacks() {
        _teacherMyFeedbacksLiveData.postValue(NetworkResult.Loading())
        val response = teacherAPI.teacherMyFeedbacks()
        if (response.isSuccessful && response.body() != null) {
            teacherMyFeedbacksLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _teacherMyFeedbacksLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _teacherMyFeedbacksLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun teacherMyRanks(type: String) {
        _teacherMyALlRanksLiveData.postValue(NetworkResult.Loading())
        Log.e("ranktype", type)
        val response = teacherAPI.teacherMyRanks(type)
        if (response.isSuccessful && response.body() != null) {
            _teacherMyALlRanksLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _teacherMyALlRanksLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _teacherMyALlRanksLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun sentimentalAnalysisData(type: String, graphSARequest: GraphSARequest) {
        _saGraphLiveData.postValue(NetworkResult.Loading())
        val response = teacherAPI.getSaGraph(type, graphSARequest)
        if (response.isSuccessful && response.body() != null) {
            _saGraphLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _saGraphLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _saGraphLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}
