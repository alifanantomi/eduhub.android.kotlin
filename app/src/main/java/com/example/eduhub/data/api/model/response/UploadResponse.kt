package com.example.eduhub.data.api.model.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("url")
    val url: String
)
