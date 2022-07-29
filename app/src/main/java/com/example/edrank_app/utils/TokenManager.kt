package com.example.edrank_app.utils

import android.content.Context
import com.example.edrank_app.utils.Constants.PREFS_TOKEN_FILE
import com.example.edrank_app.utils.Constants.TENANT_TYPE
import com.example.edrank_app.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun saveTenant(tenant: String) {
        val editor = prefs.edit()
        editor.putString(TENANT_TYPE, tenant)
        editor.apply()
    }

    fun getTenant(): String? {
        return prefs.getString(TENANT_TYPE, null)
    }
}