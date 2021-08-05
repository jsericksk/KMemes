package com.kproject.kmemes.repository

sealed class ImageResultCallback {
    class Success(val imageList: List<String>) : ImageResultCallback()
    object Error : ImageResultCallback()
}