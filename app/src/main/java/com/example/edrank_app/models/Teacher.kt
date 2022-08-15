package com.example.edrank_app.models

data class Teacher(
    val alt_email: String,
    val cid: Int,
    val course_id: Int,
    val created_at: String,
    val department: String,
    val designation: String,
    val email: String,
    val id: Int,
    val is_active: Boolean,
    val name: String,
    val password: String,
    val score: Double,
    val updated_at: String
)