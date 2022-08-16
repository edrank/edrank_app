package com.example.edrank_app.repositories

import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.ParentAPI
import com.example.edrank_app.models.ChildrenOfParentResponse
import com.example.edrank_app.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class ParentRepository @Inject constructor(private val parentAPI: ParentAPI) {
    private val _parentData = MutableLiveData<NetworkResult<ChildrenOfParentResponse>>()
    val parentData get() = _parentData

    suspend fun getChildrenOfParent() {
        _parentData.postValue(NetworkResult.Loading())
        val response = parentAPI.getChildren()
        handleResponse(response)
    }

    private fun handleResponse(response: Response<ChildrenOfParentResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _parentData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _parentData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _parentData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}