package com.example.edrank_app.ui.teacher

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.TeacherFeedbackResponse
import com.example.edrank_app.repositories.TeacherRepository
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(private val teacherRepository: TeacherRepository) :
    ViewModel() {

    val teacherMyFeedbacksLiveData: LiveData<NetworkResult<TeacherFeedbackResponse>>
        get() = teacherRepository.teacherMyFeedbacksLiveData

    fun getTeacherFeedbacks() {
        viewModelScope.launch {
            teacherRepository.teacherMyFeedbacks()
        }
    }

}