package com.example.edrank_app.ui

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.*
import com.example.edrank_app.repositories.UserRepository
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<ChangePasswordResponse>>
        get() = userRepository.userResponseLiveData

    val courseData: LiveData<NetworkResult<CourseResponse>>
        get() = userRepository.getCourse

    val teacherMyProfile: LiveData<NetworkResult<MyProfileResponse>>
        get() = userRepository.teacherMyProfile

    val collegeRank: LiveData<NetworkResult<CollegeRankResponse>>
        get() = userRepository.collegeRank

    val topNTeachers: LiveData<NetworkResult<TopTeachersResponse>>
        get() = userRepository.topNTeachers

    val topNColleges: LiveData<NetworkResult<TopCollegesResponse>>
        get() = userRepository.topNColleges

    fun getCollegeRank(collegeRankRequest: CollegeRankRequest) {
        viewModelScope.launch {
            userRepository.getCollegeRank(collegeRankRequest)
        }
    }

    fun getCourse(cId: String) {
        viewModelScope.launch {
            userRepository.getCourse(cId)
        }
    }

    fun getTopNTeachers(topTeachersRequest: TopTeachersRequest) {
        viewModelScope.launch {
            userRepository.getTopNTeachers(topTeachersRequest)
        }
    }

    fun getTopNColleges(topCollegesRequest: TopCollegesRequest) {
        viewModelScope.launch {
            userRepository.getTopNColleges(topCollegesRequest)
        }
    }

    fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch {
            userRepository.changePassword(changePasswordRequest)
        }
    }

    fun getTeacherProfile() {
        viewModelScope.launch {
            userRepository.teacherMyProfile()
        }
    }

    fun validateData(
        oldPassword: String, newPassword: String
    ): Pair<Boolean, String> {

        var result = Pair(true, "")
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)) {
            result = Pair(false, "Please provide the credentials")
        } else if (oldPassword == newPassword) {
            result = Pair(false, "Old password and new password should not be same")
        } else if (newPassword.length <= 5) {
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }
}
