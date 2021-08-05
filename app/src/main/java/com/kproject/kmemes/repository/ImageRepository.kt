package com.kproject.kmemes.repository

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImageRepository {

    suspend fun getImages(resultCallback: (resultCallback: ImageResultCallback) -> Unit) {
        val imageUrlList = mutableListOf<String>()
        withContext(Dispatchers.IO) {
            try {
                val storage = Firebase.storage
                /**
                 * Maximum time to get the image list and download URL.
                 * This can be useful to avoid "infinite" calls in case of lack
                 * of network or very slow connection, since when the connection is lost,
                 * the failure callback will not be immediately triggered by Firebase.
                 * Default maxOperationRetryTimeMillis: 2 minutes (120,000 milliseconds).
                 * Default maxDownloadRetryTimeMillis : 10 minutes (600,000 milliseconds).
                 */
                storage.maxOperationRetryTimeMillis = 15000
                storage.maxDownloadRetryTimeMillis = 30000
                val imagesReference = storage.reference.child("images/").listAll().await()
                for (image in imagesReference.items) {
                    val imageUrl = image.downloadUrl.await()
                    imageUrlList.add(imageUrl.toString())
                }
                resultCallback(ImageResultCallback.Success(imageUrlList))
            } catch (e: Exception) {
                resultCallback(ImageResultCallback.Error)
            }
        }
    }
}