package com.kproject.kmemes.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kproject.kmemes.model.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImageRepository {

    suspend fun getImages(
        memeList: String,
        resultCallback: (resultCallback: ImageResultCallback) -> Unit
    ) {
        val imageList = mutableListOf<Image>()
        withContext(Dispatchers.IO) {
            try {
                val database = Firebase.database.reference.child(memeList)
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (imageSnapshot in snapshot.children) {
                                val image = imageSnapshot.getValue(Image::class.java)
                                imageList.add(image!!)
                            }
                            resultCallback(ImageResultCallback.Success(imageList))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        resultCallback(ImageResultCallback.Error)
                    }
                })
            } catch (e: Exception) {
                resultCallback(ImageResultCallback.Error)
            }
        }
    }

    /**
     * Method used before migration to Firebase Realtime Database to get list of image URLs
     * (not recommended due to high cost for Cloud Storage and also good user experience).
     * Instead of just getting a list of URLs, which with the downloadUrl() method is expensive
     * to do in Cloud Storage, now you get a JSON that can be converted to an [Image] model.
     * Method left here unused for historical and explanation purposes only.
     */
    suspend fun getImagesFromStorage(resultCallback: (resultCallback: ImageResultCallback) -> Unit) {
        val imageList = mutableListOf<Image>()
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
                    /**
                     * The image name can be obtained via the URL. Before Realtime Database, this
                     * method only fetched a list of strings that would be the URL of each image,
                     * not a JSON set with name and URL that can be converted to [Image]
                     * model as it is now.
                     */
                    imageList.add(Image(imageUrl.toString(), "imageName"))
                }
                resultCallback(ImageResultCallback.Success(imageList))
            } catch (e: Exception) {
                resultCallback(ImageResultCallback.Error)
            }
        }
    }
}