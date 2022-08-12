package com.example.edrank_app.repositories
import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.UserAPI
import com.example.edrank_app.models.ChangePasswordRequest
import com.example.edrank_app.models.ChangePasswordResponse
import com.example.edrank_app.models.TeacherProfileResponse
import com.example.edrank_app.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {
    private val _userResponseLiveData = MutableLiveData<NetworkResult<ChangePasswordResponse>>()
    val userResponseLiveData get() = _userResponseLiveData

    private val _teacherMyProfile = MutableLiveData<NetworkResult<TeacherProfileResponse>>()
    val teacherMyProfile get() = _teacherMyProfile

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.changePassword(changePasswordRequest)
        handleResponse(response)
    }

    suspend fun teacherMyProfile() {
        _teacherMyProfile.postValue(NetworkResult.Loading())
        val response = userAPI.teacherMyProfile()
        if (response.isSuccessful && response.body() != null) {
            _teacherMyProfile.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _teacherMyProfile.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _teacherMyProfile.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    private fun handleResponse(response: Response<ChangePasswordResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}

