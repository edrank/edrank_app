package com.example.edrank_app.models

data class GrievanceCellRequest(
    val cc_response: String,
    val complaint_for: String,
    val description: String,
    val is_cc: Boolean,
    val proof_file_id: String,
    val subject: String
)