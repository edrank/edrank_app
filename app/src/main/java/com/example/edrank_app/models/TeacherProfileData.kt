package com.example.edrank_app.models

class TeacherProfileData (
    val id: Int,
    val cid : Int,
    val name : String,
    val email : String,
    val alt_email : String,
    val department : String,
    val course_id : Int,
    val designation : String,
    val score : String,
    val password : String,
    val is_active : Boolean = true,
    val created_at : String,
    val updated_at : String
)
