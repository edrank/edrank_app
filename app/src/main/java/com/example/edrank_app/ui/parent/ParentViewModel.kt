package com.example.edrank_app.ui.parent

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.ChildrenOfParentResponse
import com.example.edrank_app.repositories.ParentRepository
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ParentViewModel @Inject constructor(private val parentRepository: ParentRepository) :
    ViewModel() {

    val parentData: LiveData<NetworkResult<ChildrenOfParentResponse>>
        get() = parentRepository.parentData

    fun getChildrenOfParent() {
        viewModelScope.launch {
            parentRepository.getChildrenOfParent()
        }
    }
}