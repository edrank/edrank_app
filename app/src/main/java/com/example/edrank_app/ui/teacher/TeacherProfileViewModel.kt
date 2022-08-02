package com.example.edrank_app.ui.teacher

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.ChangePasswordRequest
import com.example.edrank_app.models.ChangePasswordResponse
import com.example.edrank_app.models.LoginResponse
import com.example.edrank_app.repositories.TeacherRepository
import com.example.edrank_app.repositories.UserRepository
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeacherProfileViewModel @Inject constructor(private val teacherRepository: TeacherRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<ChangePasswordResponse>>
        get() = teacherRepository.userResponseLiveData

    fun changePassword(changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch {
            teacherRepository.changePassword(changePasswordRequest)
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