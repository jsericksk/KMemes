package com.kproject.kmemes.repository

import com.kproject.kmemes.model.Image

sealed class ImageResultCallback {
    class Success(val imageList: List<Image>) : ImageResultCallback()
    object Error : ImageResultCallback()
}