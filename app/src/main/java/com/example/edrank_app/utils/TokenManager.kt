package com.example.edrank_app.utils

import android.content.Context
import com.example.edrank_app.utils.Constants.CID
import com.example.edrank_app.utils.Constants.COLLEGE_NAME
import com.example.edrank_app.utils.Constants.COURSE_ID
import com.example.edrank_app.utils.Constants.C_ABV
import com.example.edrank_app.utils.Constants.C_NAME
import com.example.edrank_app.utils.Constants.PREFS_TOKEN_FILE
import com.example.edrank_app.utils.Constants.TENANT_TYPE
import com.example.edrank_app.utils.Constants.USER_TOKEN
import com.example.edrank_app.utils.Constants.U_NAME
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

    fun saveCollegeId(cid: String) {
        val editor = prefs.edit()
        editor.putString(CID, cid)
        editor.apply()
    }

    fun getCollegeId(): String? {
        return prefs.getString(CID, null)
    }

    fun saveCollegeName(collegeName: String) {
        val editor = prefs.edit()
        editor.putString(COLLEGE_NAME, collegeName)
        editor.apply()
    }

    fun getCollegeName(): String? {
        return prefs.getString(C_NAME, null)
    }

    fun saveUserName(userName: String) {
        val editor = prefs.edit()
        editor.putString(U_NAME, userName)
        editor.apply()
    }

    fun getUserName(): String? {
        return prefs.getString(U_NAME, null)
    }

    fun saveCourseId(userName: String) {
        val editor = prefs.edit()
        editor.putString(COURSE_ID, userName)
        editor.apply()
    }

    fun getCourseId(): String? {
        return prefs.getString(COURSE_ID, null)
    }

    fun saveCourse(cName: String) {
        val editor = prefs.edit()
        editor.putString(C_NAME, cName)
        editor.apply()
    }

    fun getCourse(): String? {
        return prefs.getString(C_NAME, null)
    }

    fun saveCourseAbv(cAbv: String) {
        val editor = prefs.edit()
        editor.putString(C_ABV, cAbv)
        editor.apply()
    }

    fun getCourseAbv(): String? {
        return prefs.getString(C_ABV, null)
    }
}