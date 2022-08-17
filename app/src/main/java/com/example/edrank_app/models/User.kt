package com.example.edrank_app.models

data class User(
    val alt_email: String,
    val batch: String,
    val cid: String,
    val course_id: Int,
    val dob: String,
    val department: String,
    val designation: String,
    val email: String,
    val fathers_name: String,
    val guardian_email: String,
    val guardian_phone: String,
    val id: Int,
    val is_active: Boolean,
    val mothers_name: String,
    val name: String,
    val parent_id: Int,
    val phone: String,
    val score: Double,
    val year: Int
)