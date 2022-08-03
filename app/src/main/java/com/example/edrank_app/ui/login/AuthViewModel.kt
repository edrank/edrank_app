package com.example.edrank_app.ui.login

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edrank_app.models.LoginRequest
import com.example.edrank_app.models.LoginResponse
import com.example.edrank_app.repositories.AuthRepository
import com.example.edrank_app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<LoginResponse>>
        get() = authRepository.userResponseLiveData

    fun loginUser(tenant : String, loginRequest: LoginRequest){
        viewModelScope.launch {
            authRepository.loginUser(tenant, loginRequest)
        }
    }

    fun validateCredentials(emailAddress: String, password: String,
                            isLogin: Boolean) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if(TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            result = Pair(false, "Email is invalid")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }
}