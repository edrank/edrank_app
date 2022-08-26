package com.example.edrank_app.models

import android.graphics.Bitmap

data class FileUploadRequest(
    val file: Bitmap?,
    val file_type: String
)