package com.example.edrank_app.models

class MyProfileData (
    val id: Int,
    val cid : Int,
    val dob: String,
    val name : String,
    val email : String,
    val enrollment : String,
    val alt_email : String,
    val department : String,
    val course_id : Int,
    val designation : String,
    val score : String,
    val parent_id : String,
    val password : String,
    val phone : String,
    val is_active : Boolean = true,
    val created_at : String,
    val updated_at : String,
    val fathers_name: String,
    val guardian_email: String,
    val guardian_phone: String,
    val mothers_name: String,
    val year: Int,
    val batch: String
)
