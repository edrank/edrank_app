package com.example.edrank_app.repositories
import androidx.lifecycle.MutableLiveData
import com.example.edrank_app.api.UserAPI
import com.example.edrank_app.models.*
import com.example.edrank_app.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {
    private val _userResponseLiveData = MutableLiveData<NetworkResult<ChangePasswordResponse>>()
    val userResponseLiveData get() = _userResponseLiveData

    private val _getCourse = MutableLiveData<NetworkResult<CourseResponse>>()
    val getCourse get() = _getCourse

    private val _teacherMyProfile = MutableLiveData<NetworkResult<TeacherProfileResponse>>()
    val teacherMyProfile get() = _teacherMyProfile

    private val _topNTeachers = MutableLiveData<NetworkResult<TopTeachersResponse>>()
    val topNTeachers get() = _topNTeachers

    private val _topNColleges = MutableLiveData<NetworkResult<TopCollegesResponse>>()
    val topNColleges get() = _topNColleges

    private val _collegeRank = MutableLiveData<NetworkResult<CollegeRankResponse>>()
    val collegeRank get() = _collegeRank


    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.changePassword(changePasswordRequest)
        handleResponse(response)
    }

    suspend fun getCourse(cId: String){
        _getCourse.postValue(NetworkResult.Loading())
        val response = userAPI.getCourse(cId)
        if (response.isSuccessful && response.body() != null) {
            _getCourse.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _getCourse.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _getCourse.postValue(NetworkResult.Error("Something Went Wrong"))
        }

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

    suspend fun getCollegeRank(collegeRankRequest: CollegeRankRequest) {
        _collegeRank.postValue(NetworkResult.Loading())
        val response = userAPI.collegeRank(collegeRankRequest)
        if (response.isSuccessful && response.body() != null) {
            _collegeRank.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _collegeRank.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _collegeRank.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun getTopNTeachers(topTeachersRequest: TopTeachersRequest) {
        _topNTeachers.postValue(NetworkResult.Loading())
        val response = userAPI.topTeachers(topTeachersRequest)
        if (response.isSuccessful && response.body() != null) {
            _topNTeachers.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _topNTeachers.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _topNTeachers.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun getTopNColleges(topCollegesRequest: TopCollegesRequest) {
        _topNColleges.postValue(NetworkResult.Loading())
        val response = userAPI.topColleges(topCollegesRequest)
        if (response.isSuccessful && response.body() != null) {
            _topNColleges.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _topNColleges.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _topNColleges.postValue(NetworkResult.Error("Something Went Wrong"))
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

